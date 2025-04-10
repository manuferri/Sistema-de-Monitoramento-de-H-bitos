package apresentacao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.Categoria;
import model.Habito;
import model.Meta;
import model.Usuario;
import persistencia.HabitoDAO;
import persistencia.MetaDAO;
import persistencia.UsuarioDAO;
import persistencia.CategoriaDAO;

public class Main {
	static UsuarioDAO uDAO = new UsuarioDAO();
	static HabitoDAO hDAO = new HabitoDAO();
	static MetaDAO mDAO = new MetaDAO();
	static CategoriaDAO cDAO = new CategoriaDAO();
	static Scanner teclado = new Scanner(System.in);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args) {

		while(true) {
			Usuario usuario = null;
	        boolean autenticado = false;
			
			while(true) {
				System.out.println("Bem-vindo ao Wise Habit!");
				System.out.println("Menu Inicial: ");
				System.out.println("[1] Criar conta");
				System.out.println("[2] Fazer Login");
				
				int resposta = teclado.nextInt();
				teclado.nextLine();

				
				if(resposta == 1) {
					usuario = criarConta();
					autenticado = fazerLogin(usuario);
				} else {
					if(resposta == 2) {
						usuario = fazerLogin();
						autenticado = fazerLogin(usuario);
					} else {
						System.out.println("Opção incorreta.");
					}
				}
				if(autenticado) {
					break;
				}
			}
			
			
			while(true) {
				if (!autenticado) {
					break;
				}
				
				System.out.println("Bem-vindo(a) " + usuario.getNome());
	            System.out.println("[1] Gerenciar hábitos");
	            System.out.println("[2] Gerenciar metas");
	            System.out.println("[3] Editar conta");
	            System.out.println("[4] Fazer Logout");
	            System.out.println("[5] Excluir conta");

	            int rep1 = teclado.nextInt();
	            teclado.nextLine();

	            switch (rep1) {
	                case 1:
	                	while(true) {
	                		List<Habito> habitos = hDAO.buscarTodos(usuario.getId()); 
	      	              
		                    if(habitos.isEmpty()) {
		                    	System.out.println("Você não tem hábitos registrados");
		                    } else {
		                    	System.out.println("Seus hábitos: ");
		                    	for (Habito habito : habitos) {
		                    	    System.out.println("Hábito: " + habito.getNome() + ", Frequência: " + habito.getFrequencia() + ", Categoria: " + habito.getCategoria().getNome());
		                    	}
		                    	System.out.println();
		                    }
		                    
	                    	
		                    
		                    System.out.println("O que você gostaria de fazer? [criar] [editar] [excluir] [voltar]");
		                    String rep4 = teclado.nextLine();
		                    
		                    if(rep4.equals("criar")) {
		                    	criarHabito(usuario);
		                    } else {
		                    	if(rep4.equals("editar")) {
		                    		System.out.println("Qual hábito você gostaria de editar?");
		                    		String nomeHabito = teclado.nextLine();
		                    		
		                    		Habito habito = hDAO.buscarPorNome(usuario.getId(), nomeHabito);
		                    		
		                    		if (habito == null) {
		                    		    System.out.println("Hábito não encontrado.");
		                    		} else {
		                    			editarHabito(habito);
		                    		}
		           
		                    	} else {
		                    		if(rep4.equals("excluir")) {
		                    			System.out.println("Você gostaria de excluir seus hábitos?  Tem  certeza? :( ");
			                    		System.out.println("");
			                    		System.out.println("Qual hábito você gostaria de excluir?");
			                    		
			                    		String nome = teclado.nextLine();
			                    		
			                    		Habito habito = hDAO.buscarPorNome(usuario.getId(), nome);
			                    		if (habito == null) {
			                    		    System.out.println("Hábito não encontrado.");
			                    		} else {
			                    			excluirHabito(habito);
			                    		}
			                    		
		                    		} else {
		                    			if(rep4.equals("voltar")) {
		                    				break;
		                    			}
		                    		}
		                    	}
		                    }
	                	}
	                    
	                    break;
	                case 2:
	                	while(true) {
	                		List<Meta> metas = mDAO.buscarTodos(usuario.getId());
		                    
		                    if (metas.isEmpty()) {
		                        System.out.println("Você não tem metas registradas.");
		                    } else {
		                        System.out.println("Suas metas:");
		                        for (Meta meta : metas) {
		                            System.out.println("Meta: " + meta.getNome() + " | Início: " + meta.getDataInicio() + " | Fim: " + meta.getDataFim());
		                        }
		                        System.out.println();
		                    }
		                    
		                    System.out.println("O que você gostaria de fazer? [criar] [editar] [excluir] [voltar]");
		                    String rep2 = teclado.nextLine();
		                    
		                    if(rep2.equals("editar")) {
		                    	
		                    	System.out.println("Qual meta você gostaria de editar?");
		                    	
		                    	String nome = teclado.nextLine();
		                    	
		                    	Meta meta = mDAO.buscarPorNome(usuario.getId(), nome);
		                    	if (meta == null) {
		                    	    System.out.println("Meta não encontrada.");
		                    	} else {
		                    		editarMeta(meta);
		                    	}
		                    	
		                    	
		                    	
		                    } else {
		                    	if(rep2.equals("excluir")) {
		                    		System.out.println("Você gostaria de excluir suas metas?  Tem  certeza? :( ");
		                    		System.out.println("");
		                    		System.out.println("Qual meta você gostaria de excluir?");
		                    		
		                    		String nome = teclado.nextLine();
		                    		
		                    		Meta meta = mDAO.buscarPorNome(usuario.getId(), nome);
		                    		if (meta == null) {
			                    	    System.out.println("Meta não encontrada.");
			                    	} else {
			                    		excluirMeta(meta);
			                    	}
		                    		
		                    	} else {
		                    		if(rep2.equals("criar")) {
		                    			criarMeta(usuario);
		                    			
		                    		} else {
		                    			if(rep2.equals("voltar")) {
		                    				break;
		                    			}
		                    		}
		                    	}
		                    }
	                	}
	                    
	                    break;
	                case 3:
	                    editarConta(usuario);
	                    break;
	                case 4:
	                    System.out.println("Foi bom ver você! Até a próxima :)");
	                    try {
	        				Thread.sleep(2000);
	        			} catch (InterruptedException e) {
	        				e.printStackTrace();
	        			}
	                    
	                    autenticado = false;
	                    	                    
	                    break;
	                case 5:
	                    System.out.println("ATENÇÃO: todos os hábitos e metas serão excluídos!");
	                    System.out.println();
	                    System.out.println("Tem certeza? [1] sim [2] não");
	                    
	                    int rep3 = teclado.nextInt();
	                    
	                    if(rep3 == 1) {
	                    	hDAO.excluirPorUsuario(usuario.getId());
	                    	mDAO.excluirPorUsuario(usuario.getId());
	                    	uDAO.excluirPorId(usuario.getId());
	                    	autenticado = false;
	                    	
	                    	System.out.println("Sua conta foi excluída!");
		                    try {
		        				Thread.sleep(2000);
		        			} catch (InterruptedException e) {
		        				e.printStackTrace();
		        			}
	                    } else {
	                    	System.out.println("Ebaaaa!!! Ótima decisão :)");
	                    }
	                    break;
	                default:
	                    System.out.println("Opção inválida.");
	            }
			}
		}
	}
	
	public static boolean fazerLogin(Usuario usuario) {
        if (usuario != null) {
            return true;
        } else {
            return false;
        }
    }
	
	public static Usuario fazerLogin() {

		System.out.println("Email: ");
		String email = teclado.nextLine();
		
		System.out.println("Senha: ");
		String senha = teclado.nextLine();
		
		Usuario usuario = uDAO.buscarPorEmail(email);
		
		if (usuario != null && usuario.getSenha().equals(senha)) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Email ou senha incorretos.");
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
            //limpar tela e voltar pro menu principal
            usuario = null;
        }
		
		return usuario;
	}
	
	public static Usuario criarConta() {

		Usuario usuario;
		
		System.out.println("Nome: ");
		String nome = teclado.nextLine();
		
		System.out.println();
		
		System.out.println("Email: ");
		String email = teclado.nextLine();
		
		System.out.println();
		
		System.out.println("Senha: ");
		String senha = teclado.nextLine();
		
		Usuario usuarioCadastrado = uDAO.buscarPorEmail(email);
		
		if(usuarioCadastrado != null) {
			System.out.println("Já existe um usuário cadastrado com o email " + email);
			usuario = null;
		} else {
			usuario = new Usuario(0, nome, email, senha);
			
			usuario = uDAO.salvar(usuario);
			
			System.out.println();
		}
		
		return usuario;
	}
	
	public static boolean editarConta(Usuario usuario) {
		System.out.println("Qual informação você gostaria de alterar?");
		System.out.println("[1] nome [2] senha");
		
		int resposta = teclado.nextInt();
		teclado.nextLine();
		
		switch (resposta) {
		
        case 1:
            System.out.println("Digite o novo nome: ");
            usuario.setNome(teclado.nextLine());
            break;
            
        case 2:
            System.out.println("Digite a nova senha: ");
            usuario.setSenha(teclado.nextLine());
            break;
            
        default:
            System.out.println("Opção incorreta.");
            return false;
		}
		
		uDAO.editar(usuario);
		System.out.println();
		return true;
	}
	
	public static Habito criarHabito(Usuario usuario) {
		System.out.println("Nome do hábito: ");
		
		String nome = teclado.nextLine();
		
		System.out.println("Frequência: ");
		String frequencia = teclado.nextLine();
		
		
		System.out.println("Escolha uma categoria: ");
		
		List<Categoria> categorias = cDAO.buscarTodos();
		
		int i = 0;
		
		for(Categoria categoria : categorias) {
    		System.out.println("["+ (i + 1) + "] " + categoria.getNome());
    		i++;
    	}
      
		int resposta = teclado.nextInt();
		
		teclado.nextLine();
		
		Categoria categoriaEscolhida = categorias.get(resposta - 1);
		
		Habito habito = new Habito(0, nome, frequencia, categoriaEscolhida, usuario);
		
	    
	    hDAO.salvar(habito);
	    
	    System.out.println();
	    
	    return habito;
	}
	
	public static boolean editarHabito(Habito habito) {
		System.out.println("Qual informação você quer alterar?");
		System.out.println("[1] nome, [2] frequência, [3] categoria");
		
		int resposta = teclado.nextInt();
		teclado.nextLine();
		
		switch (resposta) {
        case 1:
            System.out.println("Digite o novo nome: ");
            habito.setNome(teclado.nextLine());
            break;
        case 2:
            System.out.println("Digite a nova frequência: ");
            habito.setFrequencia(teclado.nextLine());
            break;
        case 3:
            System.out.println("Digite a nova categoria: ");
            
            List<Categoria> categorias = cDAO.buscarTodos();
    		
    		int i = 0;
    		
    		for(Categoria categoria : categorias) {
        		System.out.println("["+ (i + 1) + "] " + categoria.getNome());
        		i++;
        	}
          
    		int rep5 = teclado.nextInt();
    		
    		Categoria novaCategoria = categorias.get(rep5 - 1);
    		habito.setCategoria(novaCategoria);
            break;
        default:
            System.out.println("Opção incorreta.");
            return false;
    }
		
		hDAO.editar(habito);
		System.out.println();
		return true;
	}
	
	public static boolean excluirHabito(Habito habito) {


		System.out.println("Você tem certeza? [sim] [não]");
		String resposta = teclado.nextLine();
		
		if(resposta.equals("sim")) {
			hDAO.excluirPorId(habito.getId());
			System.out.println();
			return true;
		} else {
			System.out.println("Continue evoluindo cada vez! :)");
			return false;
		}
	}
	
	public static Meta criarMeta(Usuario usuario) {

		System.out.println("Nome da meta: ");
		String nome = teclado.nextLine();
		
		Date dataInicio = new Date();
		
        Date dataFinal = null;
        
        System.out.println();
        
        System.out.println("Data final (dd/MM/yyyy): ");
        try {
            dataFinal = dateFormat.parse(teclado.nextLine());
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Tente novamente.");
        }
		
        Meta meta = new Meta(0, nome, dataInicio, dataFinal, usuario);
		
		meta = mDAO.salvar(meta);
		if (meta == null) {
		    System.out.println("Erro ao salvar a meta.");
		} else {
		    System.out.println("Meta salva com sucesso!");
		}
		
		System.out.println();
		
		return meta;
		
	}
	
	public static boolean editarMeta(Meta meta) {

		System.out.println("Qual informação você quer alterar?");
		System.out.println("[1] nome, [2] dataInicio, [3] dataFim");
		
		int resposta = teclado.nextInt();
		teclado.nextLine();
		
		switch (resposta) {
        case 1:
            System.out.println("Digite o novo nome: ");
            meta.setNome(teclado.nextLine());
            break;
        case 2:
            System.out.println("Digite a nova data de início (dd/MM/yyyy): ");
            try {
                meta.setDataInicio(dateFormat.parse(teclado.nextLine()));
            } catch (ParseException e) {
                System.out.println("Formato de data inválido.");
                return false;
            }
            break;
        case 3:
            System.out.println("Digite a nova data final (dd/MM/yyyy): ");
            try {
                meta.setDataFim(dateFormat.parse(teclado.nextLine()));
            } catch (ParseException e) {
                System.out.println("Formato de data inválido.");
                return false;
            }
            break;
        default:
            System.out.println("Opção incorreta.");
            return false;
    }
		
		mDAO.editar(meta);
		System.out.println();
		return true;
	}
	
	public static boolean excluirMeta(Meta meta) {
		System.out.println("Você tem certeza? [sim] [não]");
		String resposta = teclado.nextLine();
		
		if(resposta.equals("sim")) {
			mDAO.excluirPorId(meta.getId());
			System.out.println();
			return true;
		} else {
			System.out.println("Continue persistindo nos seus objetivos! :)");
			return false;
		}
	}
	
}
