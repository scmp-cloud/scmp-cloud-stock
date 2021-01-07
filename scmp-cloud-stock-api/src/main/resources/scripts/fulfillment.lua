local status = 1
local fulfillments = {}
local fulfillmentResolved = {}
local idx = 'FULFILL:' .. KEYS[#KEYS]
local key, balanceQty, fulfillmentQty, actuallyQty, pos, applyKey, fulfillmentKey,
      len
if redis.call('exists', idx) == 1 then
    status = 0
else
    pos = 0
    for index = 1, #KEYS - 1 do
        key = tonumber(KEYS[index] or 0)
        pos = pos + 1
        applyKey = ARGV[pos]
        pos = pos + 1
        actuallyQty = tonumber(ARGV[pos] or 0)
        for len = 1, key do
            pos = pos + 1
            fulfillmentKey = ARGV[pos]
            if fulfillmentKey == applyKey then
                fulfillmentResolved[applyKey] = fulfillmentKey
                fulfillments[fulfillmentKey] = actuallyQty
                break
            else
                balanceQty = tonumber(redis.call('hget', fulfillmentKey, 'balanceQty') or 0)
                if (actuallyQty <= balanceQty) then
                    fulfillmentResolved[applyKey] = fulfillmentKey
                    fulfillments[fulfillmentKey] = actuallyQty
                    break
                end
            end
        end
        if fulfillmentResolved[applyKey] == nil then
            status = -2
            break
        end
    end
    if status > 0 then
        for fulfillmentKey, fulfillmentQty in pairs(fulfillments) do
            if fulfillmentResolved[fulfillmentKey] ~= fulfillmentKey then
                redis.call('hincrby', fulfillmentKey, 'balanceQty', -fulfillmentQty)
            end
            redis.call('hincrby', fulfillmentKey, 'fulfillmentQty', fulfillmentQty)
            redis.call('hset', idx, fulfillmentKey, fulfillmentQty)
        end
    end
end
fulfillmentResolved['status'] = status

return cjson.encode(fulfillmentResolved)