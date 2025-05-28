class Nodo {
    int dato;
    Nodo siguiente;
    Nodo anterior;

    Nodo(int dato) {
        this.dato = dato;
    }
}

public class ListaDoble {
    private Nodo cabeza;
    private Nodo cola;

    public void insertarInicio(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = cola = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
            cabeza = nuevo;
        }
    }

    public void insertarFinal(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (cola == null) {
            cabeza = cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }
    }

    public void mostrarNormal() {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.print(actual.dato + " ");
            actual = actual.siguiente;
        }
        System.out.println();
    }

    public void mostrarReverso() {
        Nodo actual = cola;
        while (actual != null) {
            System.out.print(actual.dato + " ");
            actual = actual.anterior;
        }
        System.out.println();
    }

    public boolean eliminarPorValor(int dato) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato == dato) {
                if (actual == cabeza) {
                    cabeza = cabeza.siguiente;
                    if (cabeza != null) {
                        cabeza.anterior = null;
                    } else {
                        cola = null;
                    }
                } else if (actual == cola) {
                    cola = cola.anterior;
                    if (cola != null) {
                        cola.siguiente = null;
                    } else {
                        cabeza = null;
                    }
                } else {
                    actual.anterior.siguiente = actual.siguiente;
                    actual.siguiente.anterior = actual.anterior;
                }
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean eliminarPorPosicion(int posicion) {
        if (posicion < 0)
            return false;
        Nodo actual = cabeza;
        int index = 0;
        while (actual != null && index < posicion) {
            actual = actual.siguiente;
            index++;
        }
        if (actual == null)
            return false;
        return eliminarPorValor(actual.dato);
    }

    public void revertir() {
        Nodo actual = cabeza;
        Nodo temp = null;
        while (actual != null) {
            temp = actual.anterior;
            actual.anterior = actual.siguiente;
            actual.siguiente = temp;
            actual = actual.anterior;
        }
        if (temp != null)
            cabeza = temp.anterior;
        Nodo colaTemp = cabeza;
        while (colaTemp != null && colaTemp.siguiente != null) {
            colaTemp = colaTemp.siguiente;
        }
        cola = colaTemp;
    }

    public void eliminarDuplicados() {
        Nodo actual = cabeza;
        while (actual != null) {
            Nodo runner = actual.siguiente;
            while (runner != null) {
                if (runner.dato == actual.dato) {
                    Nodo paraEliminar = runner;
                    runner = runner.siguiente;
                    eliminarPorValor(paraEliminar.dato);
                } else {
                    runner = runner.siguiente;
                }
            }
            actual = actual.siguiente;
        }
    }

    public void insertarOrdenado(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = cola = nuevo;
            return;
        }
        if (dato <= cabeza.dato) {
            insertarInicio(dato);
            return;
        }
        if (dato >= cola.dato) {
            insertarFinal(dato);
            return;
        }
        Nodo actual = cabeza;
        while (actual != null && actual.dato < dato) {
            actual = actual.siguiente;
        }
        nuevo.siguiente = actual;
        nuevo.anterior = actual.anterior;
        actual.anterior.siguiente = nuevo;
        actual.anterior = nuevo;
    }

    public static void main(String[] args) {
        ListaDoble lista = new ListaDoble();

        lista.insertarInicio(3);
        lista.insertarInicio(2);
        lista.insertarInicio(1);
        lista.mostrarNormal();
        lista.mostrarReverso();

        lista.insertarFinal(4);
        lista.insertarFinal(5);
        lista.mostrarNormal();

        lista.eliminarPorValor(3);
        lista.mostrarNormal();

        lista.eliminarPorPosicion(2);
        lista.mostrarNormal();

        lista.insertarFinal(2);
        lista.insertarFinal(5);
        lista.mostrarNormal();

        lista.eliminarDuplicados();
        lista.mostrarNormal();

        lista.revertir();
        lista.mostrarNormal();

        lista.insertarOrdenado(3);
        lista.mostrarNormal();

        ListaDoble listaOrdenada = new ListaDoble();
        listaOrdenada.insertarOrdenado(4);
        listaOrdenada.insertarOrdenado(1);
        listaOrdenada.insertarOrdenado(3);
        listaOrdenada.insertarOrdenado(2);
        listaOrdenada.mostrarNormal();
    }
}
