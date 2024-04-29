import java.io.*;
import java.util.*;

class Nodo {
    int info;
    Nodo esq, dir, pai;
}

public class ArvoreBin {
    private Nodo raiz;

    public ArvoreBin() {
        raiz = null;
    }
    
    public Nodo alocarNodo(int valor) {
        Nodo novoNodo = new Nodo();
        novoNodo.info = valor;
        novoNodo.esq = novoNodo.dir = novoNodo.pai = null;
        return novoNodo;
    }    

    public void inserir(int valor) {
        inserir(valor, raiz);
    }

    private Nodo inserir(int valor, Nodo raiz) {
        if (raiz == null) {
            this.raiz = alocarNodo(valor);
            return this.raiz;
        }
        if (valor < raiz.info) {
            if (raiz.esq == null) {
                Nodo novoNodo = alocarNodo(valor);
                raiz.esq = novoNodo;
                novoNodo.pai = raiz;
                return raiz.esq;
            } else {
                return inserir(valor, raiz.esq);
            }
        }
        if (valor > raiz.info) {
            if (raiz.dir == null) {
                Nodo novoNodo = alocarNodo(valor);
                raiz.dir = novoNodo;
                novoNodo.pai = raiz;
                return raiz.dir;
            } else {
                return inserir(valor, raiz.dir);
            }
        }
        return null;
    }

    public void preOrdem() {
        preOrdem(raiz);
    }

    private void preOrdem(Nodo raiz) {
        if(raiz != null) {
            System.out.print(raiz.info + " ");
            preOrdem(raiz.esq);
            preOrdem(raiz.dir);
        }
    }

    public void central() {
        central(raiz);
    }

    private void central(Nodo raiz) {
        if(raiz != null) {
            central(raiz.esq);
            System.out.print(raiz.info + " ");
            central(raiz.dir);
        }
    }

    public void posOrdem() {
        posOrdem(raiz);
    }

    private void posOrdem(Nodo raiz) {
        if(raiz != null) {
            posOrdem(raiz.esq);
            posOrdem(raiz.dir);
            System.out.print(raiz.info + " ");
        }
    }

    public void remover(int valor) {
        Nodo nodo = buscar(valor);
        Nodo sub;
        
        if(nodo.esq != null) {
            if(nodo.dir != null) {
                sub = buscarMin(nodo.dir);
                if(sub == nodo.dir) {
                    sub.esq = nodo.esq;
                    nodo.esq.pai = sub;
                } else {
                    sub.pai.esq = sub.dir;
                    if(sub.dir != null) {
                        sub.dir.pai = sub.pai;
                    }
                    sub.esq = nodo.esq;
                    sub.dir = nodo.dir;
                    nodo.esq.pai = sub;
                    nodo.dir.pai = sub;
                }
            } else {
                sub = nodo.esq;
            }
        } else if(nodo.dir != null) {
            sub = nodo.dir;
        } else {
            sub = null;
        }
        if(nodo == this.raiz) {
            this.raiz = sub;
        } else if(nodo.info < nodo.pai.info) {
            nodo.pai.esq = sub;
        } else {
            nodo.pai.dir = sub;
        }
        if(sub != null) {
            sub.pai = nodo.pai;
        }
    }

    public Nodo buscar(int valor) {
        return buscar(valor, raiz);
    }

    private Nodo buscar(int valor, Nodo raiz) {
        if (raiz == null) {
            return null;
        }
        if (valor < raiz.info) {
            return buscar(valor, raiz.esq);
        }
        if (valor > raiz.info) {
            return buscar(valor, raiz.dir);
        }
        return raiz;
    }

    private Nodo buscarMin(Nodo raiz) {
        while(raiz.esq != null) {
            raiz = raiz.esq;
        }
        return raiz;
    }

    public void gerarArqDot(String filename) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
        try {
            System.out.print("\n\nArquivo aberto com sucesso!\n");
            
            // Escreve no arquivo
            out.write("digraph ArvoreBinaria {\n");
            out.write("node [shape=circle, style=filled, color=black, fillcolor=\"#9370DB\"];\n");
            out.write("edge [color=black];\n");
            escreverPreOrdemDot(raiz, out);

            out.write("}\n");

            
            // Fecha o arquivo
            out.close();
            System.out.print("Arquivo salvo com sucesso!\n");
        } catch (IOException e) {
            System.out.print("Erro ao abrir o arquivo.\n");
        }
    }

    private void escreverPreOrdemDot(Nodo raiz, BufferedWriter out) throws IOException {
        if (raiz != null) {
            if (raiz.esq != null) {
                out.write(raiz.info + " -> " + raiz.esq.info + ";\n");
            }
            if (raiz.dir != null) {
                out.write(raiz.info + " -> " + raiz.dir.info + ";\n");
            }
            escreverPreOrdemDot(raiz.esq, out);
            escreverPreOrdemDot(raiz.dir, out);
        }
    }

    public static void main(String[] args) throws IOException {
        ArvoreBin[] arvore = new ArvoreBin[5];
        for(int i = 0; i < 5; i++) {
            arvore[i] = new ArvoreBin();
        } 
        
        // Árvore 1
        int altura = 3;
        int v = 1;
        int q = 2;
        for(int i = 0; i < altura; i++) {
            v *= 2;
        }
        for(int n = 0; n <= altura; n++) {
            for(int i = 1; i < q; i++) {
                arvore[0].inserir(i*v);
            }
            v /= 2;
            q *= 2;
        }

        // Árvore 2
        arvore[1].inserir(17);
        arvore[1].inserir(12);
        arvore[1].inserir(27);
        arvore[1].inserir(10);
        arvore[1].inserir(14);
        arvore[1].inserir(21);
        arvore[1].inserir(29);

        // Árvore 3
        arvore[2].inserir(17);
        arvore[2].inserir(12);
        arvore[2].inserir(27);
        arvore[2].inserir(10);
        arvore[2].inserir(14);
        arvore[2].inserir(21);
        arvore[2].inserir(29);
        arvore[2].remover(14);
        arvore[2].remover(21);
        
        // Árvore 4
        arvore[3].inserir(165);
        arvore[3].inserir(150);
        arvore[3].inserir(176);
        arvore[3].inserir(146);
        arvore[3].inserir(152);
        arvore[3].inserir(167);
        arvore[3].inserir(180);
        arvore[3].inserir(142);
        arvore[3].inserir(148);
        arvore[3].inserir(151);
        arvore[3].inserir(155);
        arvore[3].inserir(166);
        arvore[3].inserir(171);
        arvore[3].inserir(177);
        arvore[3].inserir(186);

        // Árvore 5
        arvore[4].inserir(165);
        arvore[4].inserir(150);
        arvore[4].inserir(176);
        arvore[4].inserir(146);
        arvore[4].inserir(152);
        arvore[4].inserir(167);
        arvore[4].inserir(180);
        arvore[4].inserir(142);
        arvore[4].inserir(148);
        arvore[4].inserir(151);
        arvore[4].inserir(155);
        arvore[4].inserir(166);
        arvore[4].inserir(171);
        arvore[4].inserir(177);
        arvore[4].inserir(186);
        arvore[4].remover(180);
        arvore[4].remover(146);
        arvore[4].remover(176);
        

        for(int i = 0; i < 5; i++) {
            System.out.println("Caminhamento pré-ordem:");
            arvore[i].preOrdem();
    
            System.out.println("\nCaminhamento em ordem:");
            arvore[i].central();
    
            System.out.println("\nCaminhamento pós-ordem:");
            arvore[i].posOrdem();
            
            //Salvar no arquivo dot pra visualização 
            arvore[i].gerarArqDot("arvoreBin" + i + ".dot");
    }
    }
}