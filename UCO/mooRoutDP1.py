result = 10000

def updateResult(path:str):
    prev = path[0]
    turn = 0;
    for c in path:
        if (c != prev):
            turn += 1
    if         
        
    
def movement(pos, arr, maxN, path ):
    if (all(v == 0 for v in arr)):
        updateResult(path)
    
    
    
    
    tot = 0
    for num in arr:
        tot += num
    cnt = 0
    pos = -0.5
    out = ""
    while cnt < tot:
        left = -9999
        right = -9999
        if pos - 0.5 >= 0:
            left = arr[int(pos - 0.5)]
        if pos + 0.5 < n:
            right = arr[int(pos + 0.5)]
        if left > right:
            arr[int(pos - 0.5)] -= 1
            out += "L"
            pos -= 1
        else:
            arr[int(pos + 0.5)] -= 1
            pos += 1
            out += "R"
        cnt += 1
    print(out)
    return out

def main():
    n = int(input())
    arr = list(map(int, input().split()))
    movement(0, arr)
    
main()