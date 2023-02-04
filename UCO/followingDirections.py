
def main():
    # read the total inputs
    size = int(input()) 

    results = []
    mat = []
    for _ in range(size):
        l = list(input().split())
        row = list(l[0])
        row.append(int(l[1])) 

        mat.append(row)
    
    mat.append(list(map(int,input().split())))
    days = int(input())
    
    cords = []
    for _ in range(days):    
        cords.append(list(map(int, input().split())))

    cost = 0
    for i, row in enumerate(mat):
      if (i == size):
          continue;  
      for j, cell in enumerate(row):
        if (j == size):
            continue;
             
        if (cell=='R'):
            cost += row[size]
        else:
            cost += mat[size][j]
            
    print(cost)            

    
    for day in range(days):
        i = cords[day][0]-1
        j = cords[day][1]-1
        old = mat[i][j]
        if (old=="R"):
            diff = mat[size][j] - mat[i][size]
            mat[i][j] = "D"
        else:
            diff = mat[i][size] - mat[size][j]
            mat[i][j] = "R"    
        cost += diff
        print(cost)
            
        
main();
