#!/usr/bin/python3

import os

def summarize(inputFile, outputFile):
    readName = True
    name = ""
    table = {}
    for line in inputFile:
        if readName:
            name = line.strip()
            readName = False
        elif line[0] == '-':
            readName = True
        else:
            cmax = int(line)
            table.setdefault(name, [])
            table[name].append(cmax)

    names = list(sorted(table.keys()))
    height = max(map(len, table.values()))
    outputFile.write(';'.join(names)+'\n')
    for h in range(height):
        outputFile.write(';'.join(map(str, (table[names[i]][h] if h < len(table[names[i]]) else '' for i in range(len(names)))))+'\n')

if __name__ == '__main__':
    for filename in os.listdir("results"):
        with open("results/"+filename, "r") as inputFile, open("summary/"+filename, "w") as outputFile:
            summarize(inputFile, outputFile)
