package org.aio.activities.skills.cooking;

public enum CookingObject {

    FIRE ("Fire"),
    STOVE ("Stove", CookingType.CAKE, CookingType.MEAT, CookingType.PIE, CookingType.PIZZA),
    RANGE ("Range", CookingType.CAKE, CookingType.MEAT, CookingType.PIE, CookingType.PIZZA);

    String name;
    public CookingType[] allowedCookingTypes;

    CookingObject(final String name, final CookingType... allowedCookingTypes){
        this.allowedCookingTypes = allowedCookingTypes;
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
