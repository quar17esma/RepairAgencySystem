package com.quar17esma.controller.action;

import com.quar17esma.controller.action.impl.*;

/**
 * Possible Actions.
 */
public enum ActionEnum {
    LOGIN {
        {
            this.action = new Login();
        }
    },
    LOGOUT {
        {
            this.action = new Logout();
        }
    },
    ADD_USER {
        {
            this.action = new AddUser();
        }
    },
//    EDIT_CLIENT {
//        {
//            this.action = new EditClient();
//        }
//    },
//    EDIT_MEAL {
//        {
//            this.action = new EditMeal();
//        }
//    },
//    ADD_MEAL {
//        {
//            this.action = new AddMeal();
//        }
//    },
//    SHOW_MY_MEALS {
//        {
//            this.action = new ShowMyMeals();
//        }
//    },
//    SHOW_FOODS {
//        {
//            this.action = new ShowFoods();
//        }
//    },
//    EDIT_FOOD {
//        {
//            this.action = new EditFood();
//        }
//    },
//    ADD_FOOD {
//        {
//            this.action = new AddFood();
//        }
//    },
//    SEARCH_FOOD {
//        {
//            this.action = new SearchFood();
//        }
//    },
    CHANGE_LOCALE {
        {
            this.action = new ChangeLocale();
        }
    };

    Action action;

    public Action getCurrentAction() {
        return action;
    }
}
