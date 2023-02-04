result = []
arr = []
maxMoves = 0;
size = 0
cache = {}

def updateCache(key, value):
    global maxMoves, result, arr, size, cache
    if (not key in cache):
        cache[key] = value

def moveToNextPosition(idx, right):
    global maxMoves, result, arr, size, cache

    key = ','.join(str(x) for x in arr) + "," + str(idx) + "," + str(right)
    if (key in cache):
        return cache[key]
        
    if (len(result) == maxMoves):
        updateCache(key, True)
        return True
    
    # if (idx < -1 or idx > size or arr[idx]<=0):
    if (idx < 0 or idx > size):
        updateCache(key, False)
        return False
    
    if (idx == 0):
        if (arr[0] == 0):
            updateCache(key, False)
            return False
        else:
            result.append("R");
            arr[0] -= 1
            if (not moveToNextPosition(1, True)):
                if (not moveToNextPosition(1, False)):
                    result.pop()
                    arr[0] += 1
                    updateCache(key, False)
                    return False
                else:
                    updateCache(key, True)
                    return True
            else:
                updateCache(key, True)
                return True

    if (idx == size):
        if (arr[size - 1] == 0):
            updateCache(key, False)
            return False
        else:
            result.append("L");
            arr[size - 1] -= 1
            if (not moveToNextPosition(size - 1, False)):
                if (not moveToNextPosition(size - 1, True)):
                    result.pop()
                    arr[size - 1] += 1
                    updateCache(key, False)
                    return False
                updateCache(key, True)
                return True
            else:
                updateCache(key, True)
                return True

    
    if (right):
        if (arr[idx] == 0):
            updateCache(key, False)
            return False
        else:
            arr[idx] -= 1
            result.append("R")
            next = moveToNextPosition(idx + 1, True)
            if (not next):
                # result.append("L")
                # arr[idx - 1] -= 1
                next = moveToNextPosition(idx + 1, False)
                if (next):
                    updateCache(key, True)
                    return True
                else:
                    result.pop()
                    arr[idx] += 1
                    updateCache(key, False)
                    return False
            else:
                updateCache(key, True)
                return True
    else:
        if (arr[idx - 1] == 0):
            updateCache(key, False)
            return False
        else:
            arr[idx - 1] -= 1
            result.append("L")
            next = moveToNextPosition(idx - 1, False)
            if (not next):
                # result.pop()
                # arr[idx - 1] += 1
                # result.append("R")
                # arr[idx] -= 1
                next = moveToNextPosition(idx - 1, True)
                if (next):
                    updateCache(key, True)
                    return True
                else:
                    result.pop()
                    arr[idx - 1] += 1
                    updateCache(key, False)
                    return False
            else:
                updateCache(key, True)
                return True                

def main():
    global maxMoves, result, arr, size, cache
    n = int(input())
    arr = list(map(int, input().split()))
    for m in arr:
        maxMoves += m;
    size = len(arr)
    
    moveToNextPosition(0, True);
    print("".join(result));
    
main()


