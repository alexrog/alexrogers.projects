'''
top letters
10x10
2,3,3,4,5

'''
import random
random.seed()
letters = ["A","B","C","D","E","F","G","H","I","J"]
userShipBoard = [[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10]
userStartEnd = [[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10]
userGuessBoard = [[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10]
compShipBoard = [[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10]
compGuessBoard = [[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10]
compStartEnd = [[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10]
prob = [[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10]
compGuesses = []
target = []
first = True
incr = 0

def printSolidLine():
    print("   ",end="")
    for i in range(41):
        print("-",end="")
    

def printSquares(row, array):
    print(f"{row:<2d} ",end="")
    col = 0
    for i in range(21):
        if i %2 == 0:
            print("|",end="")
        else:
            print(f" {array[row-1][col]} ",end ="")
            col += 1
            
    

def printUserBoard():
    print("    Your Guesses                                    Your Ships")
    print("   ",end="")
    for i in range(10):
        print(f"  {letters[i]} ",end="")
    print("        ",end="")
    for i in range(10):
        print(f"  {letters[i]} ",end="")
    print()
    j = 1
    for i in range(21):
        if i % 2 == 0:
            printSolidLine()
            print("    ",end="")
            printSolidLine()
            print()
        else:
            printSquares(j,userGuessBoard)
            print("    ",end="")
            printSquares(j,userShipBoard)
            print()
            j += 1

def printProbBoard():
    print("    Comp Guesses                                    Comp Ships")
    print("   ",end="")
    for i in range(10):
        print(f"  {letters[i]} ",end="")
    print()
    j = 1
    for i in range(21):
        if i % 2 == 0:
            printSolidLine()
            print()
        else:
            printSquares(j,prob)
            print()
            j += 1

def letterToNum(letter):
    for i in range(len(letters)):
        if letter == letters[i]:
            return i+1

def numToLetter(num):
    for i in range(1,11):
        if i == num:
            return letters[i-1]

def convertToRowCol(code):
    row = int(code[1])
    col = code[0]
    try:
        row += int(code[2]) + 9
    except IndexError:
        pass
    
    row -= 1
    
    for i in range(10):
        if col == letters[i]:
            col = i
    return row, col

def determineValidEndSquare(board, startSquare, numSpaces):
    sRow, sCol = convertToRowCol(startSquare)
    sRow += 1
    sCol += 1
    valid = True
    endSquare = []
    
    # ship above current space
    if sRow >= numSpaces - 1:
        for i in range(numSpaces):
            if board[sRow - numSpaces + 1 + i][sCol - 1] == "S":
                valid = False
        if valid:
            endSquare.append([sRow - numSpaces + 2,sCol])    
    
    valid = True
    # ship below currrent space
    if 10 - sRow >= numSpaces:
        for i in range(numSpaces):
            if board[sRow - 1 + i][sCol - 1] == "S":
                valid = False
        if valid:
            endSquare.append([sRow + numSpaces,sCol])  
      
    valid = True
    # ship to right of current space
    if sCol + numSpaces <= 11:
        for i in range(numSpaces):
            if board[sRow][sCol - 1 + i] == "S":
                valid = False
        if valid:
             endSquare.append([sRow+1, sCol + numSpaces - 1])   
    
    valid = True
    # ship to left of current space
    if sCol >= numSpaces:
        for i in range(numSpaces):
            if board[sRow][sCol - numSpaces + i] == "S":
                valid = False
        if valid:
             endSquare.append([sRow+1, sCol - numSpaces + 1])
    
    for i in range(len(endSquare)):
        endSquare[i] = str(numToLetter(endSquare[i][1])) + str(endSquare[i][0])
    
    return endSquare

def compOneShipLocation(board, startEnd, numSpaces):
    while True:
        row = random.randint(0,9)
        col = random.randint(1,10)
        
        col = numToLetter(col)
        startSquare = col + str(row)
        validSquares = determineValidEndSquare(board, startSquare, numSpaces)
        if len(validSquares) == 0:
            continue
        else:
            break
    endSquare = validSquares[random.randint(0,len(validSquares)-1)]
    
    startSquare = startSquare[0] + str(int(startSquare[1])+1)
    
    drawShip(board, startEnd, startSquare, endSquare, numSpaces)
 
def inputOneShipLocation(numSpaces):
    #print(f"Location of the {numSpaces} space ship:")
    while True:
        startSquare = input("\tStarting Square: ")
        try:
            startSquare[2]
            startSquare = startSquare[0].upper() + "9"
        except IndexError:
            startSquare = startSquare[0].upper() + str(int(startSquare[1])-1)
        
        if not startSquare[0].isalpha() or not startSquare[1].isdigit():
            print("That is not a valid starting square")
            continue   
        else:
            break
        
    validSquares = determineValidEndSquare(userShipBoard, startSquare, numSpaces)
    print("\nValid End Locations: ",end="")
    for i in range(len(validSquares)):
        print(f"{validSquares[i]} ",end="")
    
    valid = True
    while valid:
        endSquare = input("\n\tEnding Square: ")
        for i in range(len(validSquares)):
            print(f"{endSquare} {validSquares[i]}")
            if endSquare == validSquares[i]:
                valid = False
                break
            
    startSquare = startSquare[0] + str(int(startSquare[1])+1)
    
    drawShip(userShipBoard, userStartEnd, startSquare, endSquare, numSpaces)

def drawShip(board, startEnd, startSquare, endSquare, numSpaces):
    sRow, sCol = convertToRowCol(startSquare)
    eRow, eCol = convertToRowCol(endSquare)
    
    if sRow == eRow:
        if sCol < eCol:
            for i in range(numSpaces):
                startEnd[sRow][sCol+i] = battleshipCode(numSpaces)
                board[sRow][sCol+i] = "S"
        else:
            for i in range(numSpaces):
                startEnd[sRow][eCol+i] = battleshipCode(numSpaces)
                board[sRow][eCol+i] = "S"
    else:
        if sRow < eRow:
            for i in range(numSpaces):
                board[sRow+i][sCol] = "S"
                startEnd[sRow+i][sCol] = battleshipCode(numSpaces)
        else:
            for i in range(numSpaces):
                startEnd[eRow+i][sCol] = battleshipCode(numSpaces)
                board[eRow+i][sCol] = "S"

def battleshipCode(num):
    global first
    global incr
    if num == 5:
        return 1
    elif num == 4:
        return 2
    elif num == 3 and first:
        incr += 1
        if incr == 3:
            incr = 0
            first = False
        return 3
    elif num == 3:
        incr += 1
        if incr == 3:
            incr = 0
            first = True
        return 4
    else:
        return 5

def inputShips():
    printUserBoard()
    if input("Do you want to randomize ships?(y/n): ") == "y":
        compOneShipLocation(userShipBoard, userStartEnd, 5)
        compOneShipLocation(userShipBoard, userStartEnd, 4)
        compOneShipLocation(userShipBoard, userStartEnd, 3)
        compOneShipLocation(userShipBoard, userStartEnd, 3)
        compOneShipLocation(userShipBoard, userStartEnd, 2)
        printUserBoard()
    else:
        inputOneShipLocation(5)
        printUserBoard()
        inputOneShipLocation(4)
        printUserBoard()
        inputOneShipLocation(3)
        printUserBoard()
        inputOneShipLocation(3)
        printUserBoard()
        inputOneShipLocation(2)
        printUserBoard()
    

def compShips():
    compOneShipLocation(compShipBoard, compStartEnd, 5)
    compOneShipLocation(compShipBoard, compStartEnd, 4)
    compOneShipLocation(compShipBoard, compStartEnd, 3)
    compOneShipLocation(compShipBoard, compStartEnd, 3)
    compOneShipLocation(compShipBoard, compStartEnd, 2)

def userGuess(): 
    
    while True:
        guess = input("What is your Guess?\n").upper()
        if len(guess) >= 2 and guess[0].isalpha() and guess[1].isdigit():
            break
    #guess = "A1"
    row, col = convertToRowCol(guess)
    if compShipBoard[row][col] == "S" or compShipBoard[row][col] == "X":
        userGuessBoard[row][col] = "X"
        compShipBoard[row][col] = "X"
        if compStartEnd[row][col] != 0:
            value = compStartEnd[row][col]
            compStartEnd[row][col] = 0
            if sunkShip(compStartEnd, value):
                return "Got a Hit!\nYou sunk a ship"
        return "Got a Hit!"
    else:
        userGuessBoard[row][col] = "O"
        return "Missed!"
 
def sunkShip(board, value):
    sunk = True
    for i in range(len(board)):
        for j in range(len(board)):
            if board[i][j] == value:
                sunk = False
    if sunk:
        for i in range(len(board)):
            for j in range(len(board)):
                if board[i][j] == value + 10:
                    board[i][j] = 6
    return sunk
    
def compGuess():
    if len(target) == 0:
        for i in range(len(userStartEnd)):
            for j in range(len(userStartEnd[0])):
                if userStartEnd[i][j] > 10:
                    target.append([i,j,'N'])
                    targetMode()
                    return
        newHuntMode()
    else:
        targetMode()
    return
 
def huntMode():
    tries = 0
    while tries < 200:
        row = random.randint(0,9)
        if row % 2 == 1:
            col = random.randrange(0,9,2)
        else:
            col = random.randrange(1,10,2)
        if compGuessBoard[row][col] == " ":
            break
        tries += 1
    while tries == 200:
        row = random.randint(0,9)
        col = random.randint(0,9)
        if compGuessBoard[row][col] == " ":
            break
    compHitOrMiss(row, col)

def targetMode():
    if len(target) == 0:
        huntMode()
        return
    row = target[0][0]
    col = target[0][1]
    direction = target[0][2]
    
    if direction == "D" and compGuessBoard[row+1][col] == " ":
        compHitOrMiss(row+1,col)
    elif direction == "U" and compGuessBoard[row-1][col] == " ":
        compHitOrMiss(row-1,col)
    elif direction == "L" and compGuessBoard[row][col-1] == " ":
        compHitOrMiss(row,col-1)
    elif direction == "R" and compGuessBoard[row][col+1] == " ":
        compHitOrMiss(row,col+1)
    else:
        if row != 0 and compGuessBoard[row-1][col] == " ":
            compHitOrMiss(row-1, col)
        elif col != 9 and compGuessBoard[row][col+1] == " ":
            compHitOrMiss(row, col+1)
        elif row != 9 and compGuessBoard[row+1][col] == " ":
            compHitOrMiss(row+1, col)
        else:
            if compGuessBoard[row][col-1] == " ":
                compHitOrMiss(row, col-1)
            else:
                del target[0]
                targetMode()
    
        
def compHitOrMiss(row, col):
    stop = False
    if userShipBoard[row][col] == "S":
        # an X located above
        if row != 0 and compGuessBoard[row-1][col] == "X" and userStartEnd[row-1][col] != 6:
            if row != 9 and compGuessBoard[row+1][col] == " ":
                stop = True
                target.append([row,col,"D"])
                if target[0][2] != "N":
                    del target[0]
        # an X located below                
        if row != 9 and compGuessBoard[row+1][col] == "X" and not stop and userStartEnd[row+1][col] != 6:
            if row != 0 and compGuessBoard[row-1][col] == " ":
                stop = True
                target.append([row,col,"U"])
                if target[0][2] != "N":
                    del target[0]
        # an X located to the left
        if col != 0 and compGuessBoard[row][col-1] == "X" and not stop and userStartEnd[row][col-1] != 6:
            if col != 9 and compGuessBoard[row][col+1] == " ":
                stop = True
                target.append([row,col,"R"])
                if target[0][2] != "N":
                    del target[0]
        # an X located to the right
        if col != 9 and compGuessBoard[row][col+1] == "X" and not stop and userStartEnd[row][col+1] != 6:
            if col != 0 and compGuessBoard[row][col-1] == " ":
                stop = True
                target.append([row,col,"L"])
                if target[0][2] != "N":
                    del target[0]
            else:
                if len(target) != 0:
                    del target[0]
        elif not stop:
            target.append([row,col,"N"])
            
        compGuessBoard[row][col] = "X"
        userShipBoard[row][col] = "X"
        printUserBoard()
        value = userStartEnd[row][col]
        userStartEnd[row][col] = value + 10
        if sunkShip(userStartEnd, value):
            if len(target) != 0 and target[0][0] == row and target[0][1] == col and target[0][2] != "N":
                del target[0]
            print("Computer Got a Hit!\nComputer sunk a ship")
            print(target)
            return
        print("Computer Got a Hit!")
    else:
        if len(target) != 0 and target[0][2] != "N":
            del target[0]
        compGuessBoard[row][col] = "O"
        userShipBoard[row][col] = "O"
        printUserBoard()
        print("Computer Missed!")
    print(target)   
 
def newCompGuess():    
    for i in range(len(userStartEnd)):
        for j in range(len(userStartEnd[0])):
            if userStartEnd[i][j] > 10:
                newTargetMode()
                return
    newHuntMode()
    
def newCompHitOrMiss(row, col):
    if userShipBoard[row][col] == "S":
        compGuessBoard[row][col] = "X"
        userShipBoard[row][col] = "X"
        printUserBoard()
        value = userStartEnd[row][col]
        userStartEnd[row][col] = value + 10
        if sunkShip(userStartEnd, value):
            print("Computer Got a Hit!\nComputer sunk a ship")
            print(target)
            return
        print("Computer Got a Hit!")
    else:
        compGuessBoard[row][col] = "O"
        userShipBoard[row][col] = "O"
        printUserBoard()
        print("Computer Missed!")
    
def gameOverCheck():
    user = True
    comp = True
    for i in range(len(userShipBoard)):
        for j in range(len(userShipBoard[0])):
            if userShipBoard[i][j] == "S":
                i = len(userShipBoard)
                comp = False
                break
    for i in range(len(compShipBoard)):
        for j in range(len(compShipBoard[0])):
            if compShipBoard[i][j] == "S":
                i = len(compShipBoard)
                user = False
                break
    if user:
        return "user"
    elif comp:
        return "computer"
    else:
        return "none"
    

def playGame():
    moves = 0
    while gameOverCheck() == "none":
        hitOrMiss = userGuess()
        if gameOverCheck() != "none":
            break
        newCompGuess()
        moves += 1
        print(f"You {hitOrMiss}")
    print(f"The {gameOverCheck()} wins in {moves} moves!")
    with open("numBattleshipMovesNewAlgorithm.txt.", "a") as f:
        f.write('\n'+str(moves))

def newHuntMode():
    global prob
    prob = [[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10]
    space5 = False
    space4 = False
    space3a = False
    space3b = False
    space2 = False
    for i in range(10):
        for j in range(10):
            if not space5 and userStartEnd[i][j] == 1:
                space5 = True
            elif not space4 and userStartEnd[i][j] == 2:
                space4 = True
            elif not space3a and userStartEnd[i][j] == 3:
                space3a = True
            elif not space3b and userStartEnd[i][j] == 4:
                space3b = True
            elif not space2 and userStartEnd[i][j] == 5:
                space2 = True
    if space5:
        validGuesses(5)
    if space4:
        validGuesses(4)
    if space3a:
        validGuesses(3)
    if space3b:
        validGuesses(3)
    if space2:
        validGuesses(2)
        
    guesses = []
    maxVal = 0
    row = 0
    col = 0
    for i in range(10):
        for j in range(10):
            if prob[i][j] > maxVal:
                maxVal = prob[i][j]
    for i in range(10):
        for j in range(10):
            if prob[i][j] == maxVal:
                guesses.append([i,j])
    tries = 0
    while tries < 200:
        num = random.randint(0,len(guesses)-1)
        row = guesses[num][0]
        col = guesses[num][1]
        
        if compGuessBoard[row][col] == " ":
            break
    while tries == 200:
        print("random")
        row = random.randint(0,9)
        col = random.randint(0,9)
        if compGuessBoard[row][col] == " ":
            break
    printProbBoard()
    newCompHitOrMiss(row,col)
    
    

def validGuesses(numSpaces):
    valid = True
    for i in range(10):
        for j in range(11-numSpaces):
            valid = True
            for k in range(numSpaces):
                if compGuessBoard[i][j+k] != " ":
                    valid = False
                    break
            if valid:
                for k in range(numSpaces):
                    prob[i][j+k] += numSpaces
    
    for i in range(11-numSpaces):
        for j in range(10):
            valid = True
            for k in range(numSpaces):
                if compGuessBoard[i+k][j] != " ":
                    valid = False
                    break
            if valid:
                for k in range(numSpaces):
                    prob[i+k][j] += numSpaces

def validTargetGuesses(numSpaces):
    increment = numSpaces
    valid = True
    for i in range(10):
        for j in range(11-numSpaces):
            valid = True
            increment = numSpaces
            for k in range(numSpaces):
                if compGuessBoard[i][j+k] == "O" or userStartEnd[i][j+k] == 6:
                    valid = False
                    break
                if userStartEnd[i][j+k] > 10:
                    increment *= 100
            if valid:
                for k in range(numSpaces):
                    prob[i][j+k] += increment
    
    for i in range(11-numSpaces):
        for j in range(10):
            valid = True
            increment = numSpaces
            for k in range(numSpaces):
                if compGuessBoard[i+k][j] == "O" or userStartEnd[i+k][j] == 6:
                    valid = False
                    break
                if userStartEnd[i+k][j] > 10:
                    increment *= 100
            if valid:
                for k in range(numSpaces):
                    prob[i+k][j] += increment
    
    for i in range(10):
        for j in range(10):
            if compGuessBoard[i][j] != " ":
                prob[i][j] = 0
    
def newTargetMode():
    global prob
    #print("Target Mode")
    #print(userStartEnd)
    prob = [[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10]
    space5 = False
    space4 = False
    space3a = False
    space3b = False
    space2 = False
    for i in range(10):
        for j in range(10):
            if not space5 and userStartEnd[i][j] == 1:
                space5 = True
            elif not space4 and userStartEnd[i][j] == 2:
                space4 = True
            elif not space3a and userStartEnd[i][j] == 3:
                space3a = True
            elif not space3b and userStartEnd[i][j] == 4:
                space3b = True
            elif not space2 and userStartEnd[i][j] == 5:
                space2 = True
    if space5:
        validTargetGuesses(5)
    if space4:
        validTargetGuesses(4)
    if space3a:
        validTargetGuesses(3)
    if space3b:
        validTargetGuesses(3)
    if space2:
        validTargetGuesses(2)
        
    guesses = []
    maxVal = 0
    row = 0
    col = 0
    for i in range(10):
        for j in range(10):
            if prob[i][j] > maxVal:
                maxVal = prob[i][j]
    for i in range(10):
        for j in range(10):
            if prob[i][j] == maxVal:
                guesses.append([i,j])
    tries = 0
    while tries < 200:
        num = random.randint(0,len(guesses)-1)
        row = guesses[num][0]
        col = guesses[num][1]
        
        if compGuessBoard[row][col] == " ":
            break
    while tries == 200:
        #print("random")
        row = random.randint(0,9)
        col = random.randint(0,9)
        if compGuessBoard[row][col] == " ":
            break
    #printProbBoard()
    newCompHitOrMiss(row,col)            
 
for i in range(5000):
    userShipBoard = [[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10]
    userStartEnd = [[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10]
    userGuessBoard = [[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10]
    compShipBoard = [[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10]
    compGuessBoard = [[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10,[" "]*10]
    compStartEnd = [[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10]
    prob = [[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10,[0]*10]
    compGuesses = []
    target = []
    first = True
    incr = 0
    inputShips()
    compShips()
    playGame()
    if i % 100 == 0:
        print(i)
