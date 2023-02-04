

result = []
arr = []
maxMoves = 0;

def nextMove(idx, right):
    global maxMoves, result, arr

    if (len(result) == maxMoves):
        return True

    # if (idx == 0 ):
    #     right = True
        
    # if (idx == len(arr)):
    #     right = False;    
    
    if (idx < 0 or idx >= len(arr) or arr[idx]<=0):
        return False
    
    arr[idx] -= 1
    
    # if (right):   # go right
    #     result.append("R")
    # else:
    #     result.append("L")
    
    if (right):   # go right
        result.append("R")
        next = True
        if (arr[idx]==0):
            next = False
        else:    
            if (idx == len(arr)-1):
                next = nextMove(idx, False)    
            else:
                next = nextMove(idx+1, True)      
        if (next):
            return True;
        else:
            result.pop()
            arr[idx] += 1
            result.append("L")
            return nextMove(idx-2, False)
    else:
        result.append("L")
        next = True
        if (arr[idx]==0):
            next = False
        else:    
            if (idx == 0):
                next = nextMove(idx, True)
            else:
                next = nextMove(idx-1, False)
        if (next):          
                return True;
        else:
            result.pop()
            arr[idx] += 1
            result.append("R")
            return nextMove(idx+2, True)
                

def main():
    global maxMoves, result, arr
    n = int(input())
    arr = list(map(int, input().split()))
    for m in arr:
        maxMoves += m;
    
    nextMove(0, True);
    print("".join(result));
    
main()


