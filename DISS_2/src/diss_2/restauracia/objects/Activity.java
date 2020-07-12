/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.objects;

/**
 * Enum aktivít čašníka
 * @author MarekPC
 */
public enum Activity {
    ORDER {
        @Override
        public String toString() {
            return "Objednávka";
        }

    },
    FOOD_DELIVERY {
        @Override
        public String toString() {
            return "Nesenie jedla";
        }

    },
    PAY {
        @Override
        public String toString() {
            return "Platenie";
        }

    }
}
