# Mahjong Hand validator
## Description
This is not the a full game of mahjong, but a program that is able to verify whether a hand is valid or invalid. Not all playable tiles will be included. The follwing tiles are included:
- Simple Tiles: 1-9 in the three different Suits (characters, bamboo and circles)
- Honour Tiles: The four winds (east, south, west & north) & the three dragons (red, white & green)
- Terminals: a subset of simple tiles. They are the termination of each suit (1 and 9 of each suit)

The criteria that are followed for a valid hand are:

- 4x set of three and 1x pair. The set of three can be a triplet (Pong, e.g. East Wind-East Wind-East Wind) or a sequence (chow, e.g. 1-2-3 of characters)
- 7x pair. There can not be any duplicate
- 13x orphans. These can only be honours, terminal and one pair. Except for the pair, there can be no duplicates

## Usage
To use it, you have to read in a file that has at least one hand of tiles, with a space in between each tile. The tiles need to be in unicode emoji format. Then, you can replace one of the files in the main in the Mahjong.java file and run the code.

## Architecture
The Mahjong hand validator consists of 3 different classes:
1. Mahjong class: Converts the file with a hand of tiles into usable tile objects and has the main in which you can input your file. This is also the class that should be run to validate your hand(s) of tiles.
2. Tile class: Describes the basic structure of a Tile object, using enums for the value (1, 2, 3, south, green etc.), the suit (Dragon, character, wind, etc), and the Type (Terminal, honour or simple). To create a Tile object, the value and suit need to be known, however the type is determined from by both the suit and the value. 
3. Hand class: This is the class where the actual validation happens. Multiple methods are used to determine whether the presented hand is valid according to one of the three criteria. Furthermore, a hand object has an ArrayList of tiles and a boolean array. The boolean array is the same size as the ArrayList and is used to keep track of which tiles have already been validated. 

## validation
To validate the program, multiple valid and invalid hands have been tested, including some edge cases. The files used for this are located in the domain/resources folder. 
