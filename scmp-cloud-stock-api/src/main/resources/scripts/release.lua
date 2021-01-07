local status = 1
local reserved = {}
local released = {}
local idx = 'RESERVED:' .. KEYS[#KEYS]
local key, reservedQty, applyQty
if redis.call('exists', idx) ~= 1 then
    status = -1
else
    reserved = redis.call('hgetall', idx)
    for i = 1, #reserved, 2 do
        key = reserved[i]
        reservedQty = tonumber(reserved[i + 1] or 0)
        released[key] = reservedQty
        if (reservedQty > 0) then
            redis.call('hincrby', key, 'balanceQty', reservedQty)
            redis.call('hincrby', idx, key, -reservedQty)
        end
    end
    redis.call('rename', idx, 'RELEASED:' .. KEYS[#KEYS])
end
released['status'] = status

return cjson.encode(released)