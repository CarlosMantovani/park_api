package com.mantovani.park_api;

import com.mantovani.park_api.web.dto.UsuarioCreateDto;
import com.mantovani.park_api.web.dto.UsuarioResponseDto;
import com.mantovani.park_api.web.dto.UsuarioSenhaDto;
import com.mantovani.park_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "file:src/test/java/com/mantovani/park_api/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "file:src/test/java/com/mantovani/park_api/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUsuario_ComUsernameEPassworValidos_RetornarUsuarioCriadoComStatus201(){
        UsuarioResponseDto responseBody = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("teste@teste.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("teste@teste.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }

    @Test
    public void createUsuario_ComUsernameEPassworInvalidos_RetornarErro422(){
        ErrorMessage responseBody = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("teste@", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("teste@teste", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_ComPassworInvalidos_RetornarErro422(){
        ErrorMessage responseBody = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("teste@teste.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("teste@teste.com", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("teste@teste", "1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void buscarUsuario_ComIdExistente_RetornarUsuarioComStatus200(){
        UsuarioResponseDto responseBody = testClient.get()
                .uri("/api/v1/usuarios/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "henrique@teste.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("henrique@teste.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

         responseBody = testClient.get()
                .uri("/api/v1/usuarios/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "henrique@teste.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("silva@teste.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

        responseBody = testClient.get()
                .uri("/api/v1/usuarios/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "silva@teste.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("silva@teste.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }

    @Test
    public void buscarUsuario_ComIdInexistente_RetornarErro404(){
        ErrorMessage responseBody = testClient.get()
                .uri("/api/v1/usuarios/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "henrique@teste.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void buscarUsuario_ComUsuarioClienteBuscandoOutroCliente_RetornarErro403(){
        ErrorMessage responseBody = testClient.get()
                .uri("/api/v1/usuarios/3")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "silva@teste.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void editarSenha_ComDadosValidos_RetornarStatus204(){
         testClient.patch()
                .uri("/api/v1/usuarios/2")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "silva@teste.com", "123456"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "121212", "121212"))
                .exchange()
                .expectStatus().isNoContent();

        testClient.patch()
                .uri("/api/v1/usuarios/3")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "fulano@teste.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "121212", "121212"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void editarSenha_ComUsuariosDiferentes_RetornarError403(){
        ErrorMessage responseBody = testClient.patch()
                .uri("/api/v1/usuarios/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "fulano@teste.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "121212", "121212"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

        responseBody = testClient.patch()
                .uri("/api/v1/usuarios/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "fulano@teste.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "121212", "121212"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void editarSenha_ComCamposInvalidos_RetornarError422(){
        ErrorMessage responseBody = testClient.patch()
                .uri("/api/v1/usuarios/3")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "fulano@teste.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.patch()
                .uri("/api/v1/usuarios/3")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "fulano@teste.com", "123456"))
                .bodyValue(new UsuarioSenhaDto("1234", "1234", "1234"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.patch()
                .uri("/api/v1/usuarios/3")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "fulano@teste.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("1234567", "1234567", "1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void editarSenha_ComInvalidaInvalidos_RetornarError400(){
        ErrorMessage responseBody = testClient.patch()
                .uri("/api/v1/usuarios/3")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "fulano@teste.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "121212", "121211"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient.patch()
                .uri("/api/v1/usuarios/3")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "fulano@teste.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("121212", "121212", "121212"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

    }

    @Test
    public void listarTodosUsuarios_ComUsernameEPasswordValidos_RetornarListaUsuarioStatus200() {
        List<UsuarioResponseDto> responseBody = testClient.get()
                .uri("/api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "henrique@teste.com", "123456"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UsuarioResponseDto.class)
                .returnResult()
                .getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull()
                .isNotEmpty()
                .hasSizeGreaterThan(0);
    }

    @Test
    public void listarTodosUsuarios_SemPermissaoAcesso_RetornarErro403() {
        ErrorMessage responseBody = testClient.get()
                .uri("/api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "silva@teste.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }
}
