
def diff(idx, str):
    count = 0;
    if (str[idx - 1] != "M"):
        count = count +1;

    if (str[idx + 1] != "O"):
        count = count +1;
    
    return count;    
        

def min_ops(s: str):
    length = len(s)

    if (length < 3):
        return -1
    elif length == 3 and s[1] != "O":
        return -1

    result = -1;
    min = length + 1
    for idx, c in enumerate(s):
        if (c == "O" and idx > 0 and idx < length-1):
          count = diff(idx,s)  
          if (count==0):
              result = (idx-1) + (length-idx-2) 
              break;
          else:
              cur = (idx-1) + (length-idx-2) + count
              if (cur < min):
                  min = cur  

    if (result == -1 and min <= length):
        result = min
    
    return result


def main():
    num_cases = int(input())
    results = [min_ops(input()) for _ in range(num_cases)]

    for result in results:
        print(result)


if __name__ == "__main__":
    main()