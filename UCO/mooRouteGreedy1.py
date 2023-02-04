result = []
arr = []
maxMoves = 0;
size = 0

def moveToNextPosition(idx, right):
    global maxMoves, result, arr, size

    if (len(result) == maxMoves):
        return True
    
    # if (idx < -1 or idx > size or arr[idx]<=0):
    if (idx < 0 or idx > size):
        return False
    
    if (idx == 0):
        if (arr[0] == 0):
            return False
        else:
            result.append("R");
            arr[0] -= 1
            if (not moveToNextPosition(1, True)):
                if (not moveToNextPosition(1, False)):
                    result.pop()
                    arr[0] += 1
                    return False
                else:
                    return True
            else:
                return True

    if (idx == size):
        if (arr[size - 1] == 0):
            return False
        else:
            result.append("L");
            arr[size - 1] -= 1
            if (not moveToNextPosition(size - 1, False)):
                if (not moveToNextPosition(size - 1, True)):
                    result.pop()
                    arr[size - 1] += 1
                    return False
                return True
            else:
                return True

    
    if (right):
        if (arr[idx] == 0):
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
                    return True
                else:
                    result.pop()
                    arr[idx] += 1
                    return False
            else:
                return True
    else:
        if (arr[idx - 1] == 0):
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
                    return True
                else:
                    result.pop()
                    arr[idx - 1] += 1
                    return False
            else:
                return True                

def main():
    global maxMoves, result, arr, size
    n = int(input())
    arr = list(map(int, input().split()))
    for m in arr:
        maxMoves += m;
    size = len(arr)
    
    moveToNextPosition(0, True);
    print("".join(result));
    
main()


