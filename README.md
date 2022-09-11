# Space-invaders
Java implements of the classic space invaders game. Project in the Object Oriented course - BIU

This project implements a version of the classic space invaders game. It is based on the sixth assignment (Popcorn) with small changes (see below). The implementation of the main features is as follow:
## Aliens formation
A sprite that responsible for the movement of the aliens (which implemented as blocks). It holds a list of the aliens, a rectangle representing the formation and other variables that control the movement. It implements a methods such as:
### toTheStart
Returns the formation to the initial location
### updateRect
Updates the rectangle dimensions when the list of the aliens is changed
### findShooter
Return an alien that is chosen randomly and capable to shoot
### shoot 
Creates a new bullet and shoot it
### move 
Moves the formation 
## Shields
Mimple blocks with the size 4 X 12, one hit point and cyan color. 
## Shots by aliens
New balls with the red color that are created using the createBullet method in the GameLevel class. The hit listener that listen to the shots hits are the shields, the frame blocks, and the spaceship. When an alien is being shot, it checks what is the color of the ball, and if the color is red the alien stays in the game.
The timing of the shots is controlled by a class variable in the AlianFormation class that holds the last time that the alien shot.
### Shots by the player
similar to the shots of the aliens, with whit color. The spaceship holds a variable that represents the last time it shot.
### SpaceShip
A Sprite, Collidable, and HitNotifier that holds (in composition) a paddle, a variable that indicates the status, and is responsible to the shooting.
