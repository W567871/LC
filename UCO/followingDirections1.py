COST = 'cost'
CHECKED = "checked"
UPDATED = "updated"
size = 0


def updateCostMatrix(mat, costMat, i, j, oldVal, newVal):
    global COST, CHECKED, size, UPDATED
    for m in range(i):
        for n in range(j):
            costMat[m][n][CHECKED] = False
            costMat[m][n][UPDATED] = False
    
    for m in range(i):
        for n in range(j):
            costMat[m][n][CHECKED] = False
            costMat[m][n][UPDATED] = False

    
    
    

def getNewCost(mat, costMat, i, j):
    global COST, CHECKED, size
    n = 0
    if (mat[i][j] == "D"):
        if (j == size):
            n = mat[i - 1][j]
        else:
            n = costMat[i - 1][j]    
    elif (mat[i][j] == "R"):
        if (i == size):
            n = mat[i][j - 1]
        else:
            n = costMat[i][j - 1]    
             
    return n
    
def updateCowsCost(cows, costMat, cost):
    for cow in cows:
        costMat[cow[0]][cow[1]][COST] = cost
    

def updateFeedCost(cows, mat, costMat, i, j):
    global COST, CHECKED, size

    if (i == size or j == size):
        cost = mat[i][j]
        updateCowsCost(cows, costMat, cost)
        return
    else:
        if (costMat[i][j][COST] != -1):   
            updateCowsCost(cows, costMat, costMat[i][j][COST])             
        elif (not costMat[i][j][CHECKED]):
            cows.append([i,j])
            costMat[i][j][CHECKED] = True
            if (mat[i][j] == "D"):
                updateFeedCost(cows, mat, costMat, i + 1, j)
            elif (mat[i][j] == "R"):
                updateFeedCost(cows, mat, costMat, i, j + 1)
            else:
                raise ValueError('Should not be here A')        
        else:
            raise ValueError('Should not be here B')        


def main():
    global COST, CHECKED, size
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

    #build cost matrix
    costMat = [ [{}]*size for i in range(size)]
    for i in range(size):
        for j in range(size):
            costMat[i][j] = {}
            costMat[i][j][COST] = -1
            costMat[i][j][CHECKED] = False

    for i in range(size):
        for j in range(size):
            if (not costMat[i][j][CHECKED]):
                cows = []
                updateFeedCost(cows, mat, costMat, i, j)

   
    cost = 0
    for i in range(size):
        for j in range(size):
            cost += costMat[i][j][COST]
    
            
    print(cost)            

    
    for day in range(days):
        i = cords[day][0]-1
        j = cords[day][1]-1
        
        oldVal = costMat[i][j][COST]        
        newVal = getNewCost(mat, costMat, i, j)
        
        diff = 0;
        if (oldVal != newVal):
            updateCostMatrix(mat, costMat, i, j, oldVal, newVal)
                
        
        
        cost += diff
        print(cost)
            
        
main();
