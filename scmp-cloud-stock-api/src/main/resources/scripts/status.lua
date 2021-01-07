local status = 1
local initialized = {}
local balance = {}
local idx = 'STATUS:' .. KEYS[#KEYS]
local key, prop, qty
if redis.call('exists', idx) == 1 then
    status = 0
else
    for index = 1, #KEYS - 1 do
        key = KEYS[index]
        balance = redis.call('hgetall', key)
        for i = 1, #balance, 2 do
            prop = balance[i]
            qty = tonumber(balance[i + 1] or 0)
            initialized[key .. '-' .. prop] = qty
        end
    end
end
initialized['status'] = status

return cjson.encode(initialized)