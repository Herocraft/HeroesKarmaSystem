HeroesKarmaSystem
=================

Karma system using the heroes API. Current implementation is a Plugin Base that handles all i/o involving karma effects
that use the com.herocraftonline.heroes.characters.effects.Effect type to store karma data about a user.

Karma effects follow the common theme of using "Karma-nameHere" as a name for the different effect types: that is how
we determine what is a karma effect and what is not. Consequently, ANY effect type that uses Karma-anything as a name will
automatically fall under this category.
