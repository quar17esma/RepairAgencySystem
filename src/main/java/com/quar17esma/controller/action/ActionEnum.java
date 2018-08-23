package com.quar17esma.controller.action;

import com.quar17esma.controller.action.impl.*;

/**
 * Possible Commands.
 */
public enum ActionEnum {
    LOGIN {
        {
            this.command = new Login();
        }
    },
    LOGOUT {
        {
            this.command = new Logout();
        }
    };
//    ADD_CLIENT {
//        {
//            this.command = new AddClient();
//        }
//    },
//    EDIT_CLIENT {
//        {
//            this.command = new EditClient();
//        }
//    },
//    EDIT_MEAL {
//        {
//            this.command = new EditMeal();
//        }
//    },
//    ADD_MEAL {
//        {
//            this.command = new AddMeal();
//        }
//    },
//    SHOW_MY_MEALS {
//        {
//            this.command = new ShowMyMeals();
//        }
//    },
//    SHOW_FOODS {
//        {
//            this.command = new ShowFoods();
//        }
//    },
//    EDIT_FOOD {
//        {
//            this.command = new EditFood();
//        }
//    },
//    ADD_FOOD {
//        {
//            this.command = new AddFood();
//        }
//    },
//    SEARCH_FOOD {
//        {
//            this.command = new SearchFood();
//        }
//    },
//    CHANGE_LOCALE {
//        {
//            this.command = new ChangeLocale();
//        }
//    };

    Action command;

    public Action getCurrentCommand() {
        return command;
    }
}
