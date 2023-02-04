def movement():
    n = int(input())
    a = list(map(int, input().split()))
    tot = 0
    for num in a:
        tot += num
    cnt = 0
    pos = -0.5
    out = ""
    while cnt < tot:
        left = -9999
        right = -9999
        if pos - 0.5 >= 0:
            left = a[int(pos - 0.5)]
        if pos + 0.5 < n:
            right = a[int(pos + 0.5)]
        if left > right:
            a[int(pos - 0.5)] -= 1
            out += "L"
            pos -= 1
        else:
            a[int(pos + 0.5)] -= 1
            pos += 1
            out += "R"
        cnt += 1
    print(out)
    return out
    
movement()