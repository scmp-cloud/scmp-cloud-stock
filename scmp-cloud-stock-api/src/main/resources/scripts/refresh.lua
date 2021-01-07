local status = 1
local initialized = {}
local idx = 'REFRESH:' .. KEYS[#KEYS]
local key, qty, initializedQty, balanceQty, reservedQty, releasedQty,
      commitedQty, increasedQty, reducedQty, fulfillmentQty
if redis.call('exists', idx) == 1 then
    status = 0
else
    for index = 1, #KEYS - 1 do
        key = KEYS[index]
        initializedQty = tonumber(ARGV[8 * (index - 1) + 1] or 0)
        balanceQty = tonumber(ARGV[8 * (index - 1) + 2] or 0)
        reservedQty = tonumber(ARGV[8 * (index - 1) + 3] or 0)
        releasedQty = tonumber(ARGV[8 * (index - 1) + 4] or 0)
        commitedQty = tonumber(ARGV[8 * (index - 1) + 5] or 0)
        increasedQty = tonumber(ARGV[8 * (index - 1) + 6] or 0)
        reducedQty = tonumber(ARGV[8 * (index - 1) + 7] or 0)
        fulfillmentQty = tonumber(ARGV[8 * (index - 1) + 8] or 0)

        redis.call('hset', key, 'initializedQty', initializedQty)
        redis.call('hset', key, 'balanceQty', balanceQty)
        redis.call('hset', key, 'reservedQty', reservedQty)
        redis.call('hset', key, 'releasedQty', releasedQty)
        redis.call('hset', key, 'commitedQty', commitedQty)
        redis.call('hset', key, 'increasedQty', increasedQty)
        redis.call('hset', key, 'reducedQty', reducedQty)
        redis.call('hset', key, 'fulfillmentQty', fulfillmentQty)

        initialized[key .. '-initializedQty'] = initializedQty
        initialized[key .. '-balanceQty'] = balanceQty
        initialized[key .. '-reservedQty'] = reservedQty
        initialized[key .. '-releasedQty'] = releasedQty
        initialized[key .. '-commitedQty'] = commitedQty
        initialized[key .. '-increasedQty'] = increasedQty
        initialized[key .. '-reducedQty'] = reducedQty
        initialized[key .. '-fulfillmentQty'] = fulfillmentQty
    end
    for key, qty in pairs(initialized) do redis.call('hset', idx, key, qty) end
end
initialized['status'] = status

return cjson.encode(initialized)