package com.eiji.dev.ruufchallenge;

/**
 * @author Esteban Perafan - Eiji
 * 
 * ¿Cuantos paneles caben en un techo?
 * 
 * El problema a resolver consiste en encontrar la máxima cantidad de rectángulos de dimensiones “a” y “b” (paneles solares) que caben dentro de un rectángulo de dimensiones “x” e “y” (techo)
 * 
 * - El algoritmo debe ser una sola función que reciba las dimensiones y retorne un solo integer con la cantidad de paneles que caben.
 * - No hay restricciones de orientación. Pon todos los rectángulos que puedas en la posición y sentido que prefieras.
 * 
 * EJEMPLOS:
 * - Paneles 1x2 y techo 2x4 -> Caben 4
 * - Paneles 1x2 y techo 3x5 -> Caben 7
 * - Paneles 2x2 y techo 1x10 -> Caben 0
 * 
 * Bonus Opcional
 * Opción 1: Repetir el ejercicio base, considerando un techo triangular, isóceles.
 */

public class RuufChallenge {

    public static void main(String[] args) {
        RuufChallenge rc = new RuufChallenge();
        
        // Ejercicio base
        System.out.println("Paneles 1x2 y techo 2x4 -> Caben " + rc.howManySolarPanelsFitOnRectangleRoof(2, 4, 1, 2) + " paneles."); //4
        System.out.println("Paneles 1x2 y techo 3x5 -> Caben " + rc.howManySolarPanelsFitOnRectangleRoof(3, 5, 1, 2) + " paneles."); //7
        System.out.println("Paneles 2x2 y techo 1x10 -> Caben " + rc.howManySolarPanelsFitOnRectangleRoof(1, 10, 2, 2) + " paneles."); //0
        System.out.println("Paneles 2x1 y techo 1x10 -> Caben " + rc.howManySolarPanelsFitOnRectangleRoof(1, 10, 2, 1) + " paneles."); //Caben 5 pero hay que rotar al panel solar
        
        // Bonus opcional 1
        System.out.println("Paneles 5x3 y techo triangular isoscel de h = 15 y a = 14  -> Caben " + rc.howManySolarPanelsFitOnTriangleRoof(14.0, 15.0, 5.0, 3.0) + " paneles.");
        System.out.println("Paneles 2x1 y techo triangular isoscel de h = 7 y a = 8 -> Caben " + rc.howManySolarPanelsFitOnTriangleRoof(8.0, 7.0, 2.0, 1.0) + " paneles.");
    }
    
    /**
     * Este método calcula cuántos paneles caben en un techo rectangular sin importar la orientación. 
     * Si los paneles caben directamente en cualquiera de las dos orientaciones posibles (horizontal o vertical), 
     * se calcula el área del techo y se divide por el área de los paneles para obtener la cantidad de paneles que caben. 
     * Si no caben en ninguna orientación, retorna 0.
     */
    public int howManySolarPanelsFitOnRectangleRoof(int roofWidth, int roofHeight, int panelWidth, int panelHeight){
        // Comprobar si los paneles caben directamente o despues de rotarlos 90 grados
        if ((panelWidth <= roofWidth && panelHeight <= roofHeight) || (panelHeight <= roofWidth && panelWidth <= roofHeight)) {
            int roofArea = roofWidth * roofHeight;
            int panelArea = panelWidth * panelHeight;
            return roofArea / panelArea;
        }

        // Si los paneles no caben en ninguna orientación, retornar 0
        return 0;
    }
    
    /**
     * Este método calcula cuántos paneles caben en un techo triangular isoscel en ambas orientaciones (horizontal y vertical) y retorna el mayor de los dos resultados.
     */
    public int howManySolarPanelsFitOnTriangleRoof(double roofWidth, double roofHeight, double panelWidth, double panelHeight) {
        int horizontalRectangle = countRectangles(roofWidth, roofHeight, panelWidth, panelHeight);
        //System.out.println("Los que caben en horizontal: " + horizontalRectangle);

        int verticalRectangle = countRectangles(roofWidth, roofHeight, panelHeight, panelWidth);
        //System.out.println("Los que caben en vertical: " + verticalRectangle);
        
        // Retornar la rotación que devolvió más rectángulos en el interior
        return Math.max(horizontalRectangle, verticalRectangle);
    }

    /**
     * Este método cuenta la cantidad de paneles que caben en el triángulo dividiendo el techo en una cuadrícula y verificando si cada celda de la cuadrícula está completamente dentro del triángulo.
    */
    public int countRectangles(double roofWidth, double roofHeight, double panelWidth, double panelHeight) {
        int rectangles = 0;

        // La cantidad de celdas con el ancho del panel que caben a lo ancho del triángulo
        int MAX_COLUMNS = (int)(roofWidth / panelWidth) + 1;
        // La cantidad de celdas con el alto del panel que caben a lo alto del triángulo
        int MAX_ROWS = (int)(roofHeight / panelHeight) + 1;

        // Recorrer cada fila de celdas de la grilla sobre el triángulo
        for (int i = 0; i < MAX_ROWS; i++) {
            // Recorrer cada columna de celdas de la grilla sobre el triángulo
            for (int j = 0; j < MAX_COLUMNS; j++) {
                // Calcular las coordenadas de todos los vértices de la celda
                double yBottom = i * panelHeight;
                double yTop = (i + 1) * panelHeight;
                double xLeft = j * panelWidth - roofWidth / 2;
                double xRight = (j + 1) * panelWidth - roofWidth / 2;
                
                /*
                CODIGO PARA DEBUG
                System.out.println("Fila: " + i);
                System.out.println("    Columna: " + j);
                System.out.println("        Vértices: ");
                System.out.println("            Izquierdo inferior (" + xLeft + ", " + yBottom + ") Entra: " + fitInTriangle(roofWidth, roofHeight, xLeft, yBottom) + " -> Altura de la recta del lado del triángulo en x (" + xLeft + ") es de " + (roofHeight - ((2 * roofHeight / roofWidth) * Math.abs(xLeft))));
                System.out.println("            Izquierdo superior (" + xLeft + ", " + yTop + ") Entra: " + fitInTriangle(roofWidth, roofHeight, xLeft, yTop) + " -> Altura de la recta del lado del triángulo en x (" + xLeft + ") es de " + (roofHeight - ((2 * roofHeight / roofWidth) * Math.abs(xLeft))));
                System.out.println("            Derecha inferior (" + xRight + ", " + yBottom + ") Entra: " + fitInTriangle(roofWidth, roofHeight, xRight, yBottom) + " -> Altura de la recta del lado del triángulo en x (" + xRight + ") es de " + (roofHeight - ((2 * roofHeight / roofWidth) * Math.abs(xRight))));
                System.out.println("            Derecha superior (" + xRight + ", " + yTop + ") Entra: " + fitInTriangle(roofWidth, roofHeight, xRight, yTop) + " -> Altura de la recta del lado del triángulo en x (" + xRight + ") es de " + (roofHeight - ((2 * roofHeight / roofWidth) * Math.abs(xRight))));
                */

                // Validar que la altura de cada vértice de la celda sea menor o igual a la altura de la recta de los lados del triángulo en esa misma posición x del vértice de la celda (para todos los vértices)
                if (fitInTriangle(roofWidth, roofHeight, xLeft, yBottom) &&
                    fitInTriangle(roofWidth, roofHeight, xLeft, yTop) &&
                    fitInTriangle(roofWidth, roofHeight, xRight, yBottom) &&
                    fitInTriangle(roofWidth, roofHeight, xRight, yTop)) {
                    // Si se cumple para todos los vértices entonces es un rectángulo que cabe dentro del triángulo y se aumenta el contador
                    rectangles++;
                }
            }
        }

        return rectangles;
    }

    /**
     * Esta función verifica si un vértice de un panel está dentro del triángulo, comparando la coordenada y del vértice con la altura de la línea del triángulo en esa coordenada x.
     */
    public boolean fitInTriangle(double triangleWidth, double triangleHeight, double xVertice, double yVertice) {
        // Obtener la altura de la recta del lado del triángulo en función de la ubicación x del vértice
        // Se resta a la altura máxima del triángulo la tasa de cambio que hubo en el eje y por unidades de x
        double lineHeight = triangleHeight - ((2 * triangleHeight / triangleWidth) * Math.abs(xVertice));

        return yVertice <= lineHeight;
    }

}
