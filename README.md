## Romain Chanoir, Johan Kayser - Travail de session - INF7341 - Polygones convexes


## Prérequis : 
- Java JDK 8
- Aucune librairie ou dépendance nécessaire


## Explications pour l'utilisation du programme.

### Compilation du programme 

javac Session_SDD.java

### Exécution du programme

java Session_SDD

### Commandes du programme 

* On peut sélectionner n'importe quel polygone de l'application en cliquant à l'intérieur avec le clic gauche, le polygone sélectionné s'affiche alors en bleu.
* Une fois sélectionné, on peut déplacer le polygone avec les touches directionnelles.
* La détection de collision se fait entre le polygone sélectionné et tous les autres de l'application.
* Les polygones qui intersectent le polygone sélectionné sont alors affichés en rouge.
lorsqu'un polygone est sélectionné, on peut lui rajouter des sommets à l'aide du clic droit. Si le point que l'on essaie d'ajouter garde le polygone convexe, il est bien ajouté.
* Sinon, un message d'erreur apparaît en haut au centre de la fenêtre, prévenant que l'ajout rendrait le polygone concave et donc le sommet n'est pas ajouté.
* On peut aussi supprimmer les sommets du polygone sélectionné, pour cela il faut appuyer sur la touche 'd' (pour delete), cela supprime un des sommets de façon aléatoire.
* Pour déselectionner un polygone, on utilise la touche echap.
* Lorsqu'aucun polygone n'est sélectionné, un clic droit ajoute un point dans une liste, lorsque trois points ont été ajouté à cette liste, un polygone
est créé, ses trois sommets sont les trois points de la liste et la liste est vidée, il y a donc un polygone de plus dans l'application qui pourra à sont tour être sélectionné,
on pourra aussi lui ajouter des points.
* Queqlues polygones sont déjà présents au démarrage de l'application :
    * Au milieu un polygone basique d'exemple pour pouvoir le sélectionner, lui ajouter des points, faire des tests d'intersection avec les autres polygones.
    * En haut à gauche, c'est un polygone généré avec un algorithme de calcul d'enveloppe convexe d'un ensemble de points. Pour celui-ci, l'ensemble de points utilisé est affiché. On peut générer aléatoirement d'autres polygones de cette façon en utilisant la touche 'r' (comme random, car l'ensemble de points est généré aléatoirement).
    * En bas à droite, on a arbitrairement créé un polygone dont les sommets ne sont pas dans le bon ordre. Il représente un sablier au lancement de l'application, mais en réordonnant les points, cela donnerait un carré. Ce polygone sert d'exemple pour notre algorithme de tri de points. En appuyant sur la touche 's' (comme sort). On enclenche le tri des points de ce polygone, qui devient alors un carré.
    * Enfin, en bas à droite, un polygone arbitrairement concave, qui est présent pour illustrer le fait que les algorithmes implémentés sont spécifiques aux polygones convexes. En effet, les algorithmes implémentés ont un comportement plus ou moins indéterminé dans le cas de ce polygone.
	
## Pour lancer les benchmarks.

### Compilation du programme

javac Benchmarks.java

### Exécution du programme

java Benchmarks

L'exécution crée un fichier texte avec les résultats obtenus.


