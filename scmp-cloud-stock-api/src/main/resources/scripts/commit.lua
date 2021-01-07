local status = 1
local reserved = {}
local reservedMap = {}
local used = {}
local idx = 'RESERVED:' .. KEYS[#KEYS]
local key, applyQty, reservedQty, commitedQty, diffQty
if redis.call('exists', idx) ~= 1 then
    status = -1
else
    reserved = redis.call('hgetall', idx)
    -- 有传明细
    if #KEYS > 1 then
        for i = 1, #reserved, 2 do
            key = reserved[i]
            reservedQty = tonumber(reserved[i + 1] or 0)
            reservedMap[key] = reservedQty
        end
        for index = 1, #KEYS - 1 do
            key = KEYS[index]
            applyQty = tonumber(ARGV[index] or 0)
            reservedQty = tonumber(reservedMap[key] or 0)
            used[key] = (applyQty <= reservedQty) and applyQty or reservedQty
            if applyQty > reservedQty then status = -2 end
        end
    end

    if (status > 0) then
        for i = 1, #reserved, 2 do
            key = reserved[i]
            reservedQty = tonumber(reserved[i + 1] or 0)
            commitedQty = (used[key] or reservedQty)
            diffQty = reservedQty - commitedQty
            redis.call('hincrby', key, 'reservedQty', -reservedQty)
            redis.call('hincrby', key, 'commitedQty', commitedQty)
            if (diffQty > 0) then
                redis.call('hincrby', key, 'balanceQty', diffQty)
            end
            used[key] = commitedQty
            redis.call('hset', idx, key, commitedQty)
        end
        redis.call('rename', idx, 'COMMITED:' .. KEYS[#KEYS])
    end
end
used['status'] = status

return cjson.encode(used)