def targetDistance(start, input):
    distance = 0;
    if (input[start - 1] != "M"):
        distance = distance +1;

    if (input[start + 1] != "O"):
        distance = distance +1;
    
    return distance;    
        

def findMinimum(input):
    size = len(input)

    if (size < 3):
        return -1

    ans = -1;
    minimumActions = size + 1
    for index, c in enumerate(input):
        if (c == "O" and index > 0 and index < size-1):
          distance = targetDistance(index,input)  
          if (distance==0):
              ans = (index-1) + (size-index-2) 
              return ans;
          else:
              cur = (index-1) + (size-index-2) + distance
              if (cur < minimumActions):
                  minimumActions = cur  

    if (minimumActions <= size):
        ans = minimumActions
    
    return ans

def main():
    total = int(input())
    answers = [findMinimum(input()) for _ in range(total)]

    for ans in answers:
        print(ans)

main()