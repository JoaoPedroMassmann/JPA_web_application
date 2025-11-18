package br.edu.utfpr.pb.pw44s.server.dto.requestdto;

import java.util.List;

public class PedidoRequestDTO {
    private String formaPagamento;
    private String formaEntrega;

    // Buscar o usuário autenticado no SecurityContextHolder
    // Veio lá do AuthService getAuthenticatedUser()
    // private User user;

    // A data do pedido vai ficar no model apenas
    //private LocalDateTime datapedido

    List<ItemPedidoDTO> itensPedido;
    private EnderecoDTO endereco;
}

/*
public class PedidoServiceImpl
   private AuthService authService;
   private Pedido save(Pedido pedido) {
      pedido.setUser(authServicer.getAuthenticatedUser())
   }
}
    fazer: post e get, sem put ou delete.

 */
