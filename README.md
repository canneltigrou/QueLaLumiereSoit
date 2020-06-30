# QueLaLumiereSoit
## What is it?
This is a multi-agent system in Java and a server.
It was implemented for an international event in Toulouse (ESOF), for the project pictonique-odysee-alpha <https://www.sultra-barthelemy.eu/pictonique-odyssee-alpha/>

The objectives were to implement little blobs that can have differents forms and colors and can change their appearance depending on their environment.
Another application has been developed (by SoitFranc) and is available on the playstore (pictonique). During the exposition the public could interact with its phone: thanks to the phone application, they could adopt a blob, then the blob is geolocalised and in this computer application, the blob can react in function of its location.

This project is based on the AMAK framework : a framework created by a doctorant Alexandre PERLES in IRIT to make multi-agent system <https://bitbucket.org/perlesa/amak/src/master/>

Here you can find some explainations about the blob community : <https://docs.google.com/document/d/1nt46j8AMAk6Vu8_FK4v0kPJ1JcHwnNgxo8oEHHUXX8s/edit?usp=sharing>


## Some clues if you want to change some details :

The event occured in the "boule du CEMES", which was  a circulate room. If you want to make the project into a squared room, you need to make some changes.

**Changer la forme du terrain :**
Changer la forme du terrain concerne plusieurs changements :
- au niveau de l'IHM : RDV dans la classe TerrainForm si vous voulez changer Ti et Tr, ou dans ToForm si vous voulez changer To.
- au niveau AMAS : l'agent doit connaître également le terrain sur lequel il peut se déplacer. 
	- amak.MyAMAS#onInitialConfiguration : (création des 1ers agents à une coordonnée aléatoire dans TO) il faut changer l'instruction "double[] coo = genererCoordonneeCercle();"
	- amak.MyEnvironment#isValideInTo ou amak.MyEnvironment#isValideInTi : retourne vrai si la coordonée donnée en paramètre n'est pas hors-map.



