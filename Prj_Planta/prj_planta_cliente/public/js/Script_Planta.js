var itensPagina = 4;
var contarPagina = 0;
var lstP = [];
var conteudoCampos = null;

// Configuração do servidor
var serv_porta = ":8084";
var serv_local = (window.location.hostname || "localhost") + serv_porta;
var servico = "plantas";

function obterUrl() {
    return "http://" + serv_local + "/api/sistemas/" + servico;
}

// SALVAR OU ATUALIZAR
function salvar() {
    var genero = $("#txtPlanta").val().trim();

    if (genero === "") {
        alert("Informe o gênero da planta.");
        $("#txtPlanta").focus();
        return;
    }

    var obj_Planta = { genero: genero };

    if ($("#txtId").length > 0) {
        obj_Planta.idplanta = Number($("#txtId").val());
    }

    var xmlHTTP_POST = new XMLHttpRequest();
    xmlHTTP_POST.open("POST", obterUrl(), false);
    xmlHTTP_POST.setRequestHeader("Content-Type", "application/json");
    xmlHTTP_POST.send(JSON.stringify(obj_Planta));

    if (xmlHTTP_POST.status === 200 || xmlHTTP_POST.status === 201) {
        novo();
        consultar();
    } else {
        alert("Erro ao salvar planta: " + xmlHTTP_POST.status + "\n" + xmlHTTP_POST.responseText);
    }
}

// EDITAR
function editar(indexItem) {
    if (!lstP[indexItem]) return;

    if ($("#txtId").length === 0) {
        conteudoCampos = $("#compoId").html();
        $("#compoId").prepend(
            '<div class="col-sm-3" id="campoIdPlanta">' +
            '<label for="txtId">ID:</label>' +
            '<input type="text" class="form-control" id="txtId" name="txtId" disabled>' +
            '</div>'
        );
    }

    $("#txtId").val(lstP[indexItem].idplanta);
    $("#txtPlanta").val(lstP[indexItem].genero);
    $("#btnNovo").prop("disabled", false);
}

// EXCLUIR
function excluir(indexItem) {
    if (!lstP[indexItem]) return;
    if (!confirm("Deseja realmente excluir esta planta?")) return;

    var obj_Planta = {
        idplanta: lstP[indexItem].idplanta,
        genero: lstP[indexItem].genero
    };

    var xmlHTTP_DELETE = new XMLHttpRequest();
    xmlHTTP_DELETE.open("DELETE", obterUrl(), false);
    xmlHTTP_DELETE.setRequestHeader("Content-Type", "application/json");
    xmlHTTP_DELETE.send(JSON.stringify(obj_Planta));

    if (xmlHTTP_DELETE.status === 200 || xmlHTTP_DELETE.status === 204) {
        novo();
        consultar();
    } else {
        alert("Erro ao excluir planta: " + xmlHTTP_DELETE.status + "\n" + xmlHTTP_DELETE.responseText);
    }
}

// LIMPAR FORMULÁRIO / NOVO CADASTRO
function novo() {
    if ($("#txtId").length > 0) {
        $("#compoId").html(conteudoCampos);
    }

    $("#txtPlanta").val("");
    $("#btnNovo").prop("disabled", true);
}

// CONSULTAR
function consultar() {
    var xmlHTTP_GET = new XMLHttpRequest();
    xmlHTTP_GET.open("GET", obterUrl(), false);
    xmlHTTP_GET.send();

    if (xmlHTTP_GET.status === 200) {
        try {
            lstP = JSON.parse(xmlHTTP_GET.responseText) || [];
            contarPagina = 0;
            preencherTabela(lstP);
            botoesConfig(lstP);
        } catch (erro) {
            alert("A resposta da API de plantas não está em um formato JSON válido.");
            console.error(erro);
        }
    } else {
        alert("Erro ao consultar plantas: " + xmlHTTP_GET.status + "\n" + xmlHTTP_GET.responseText);
    }
}

// PREENCHER TABELA
function preencherTabela(lista) {
    var tbody = $("#tabItens tbody");
    tbody.empty();

    if (!lista || lista.length === 0) {
        tbody.append(
            $("<tr>").append(
                $("<td>", { colspan: 3, class: "text-center" }).text("Nenhuma planta cadastrada.")
            )
        );
    } else {
        for (var i = contarPagina * itensPagina;
            i < lista.length && i < (contarPagina + 1) * itensPagina; i++) {
            var linha = $("<tr>");
            linha.append($("<td>").text(lista[i].genero ?? ""));

            var botaoEditar = $("<button>", {
                type: "button", class: "btn btn-info", text: "Editar"
            }).on("click", (function (indice) {
                return function () { editar(indice); };
            })(i));

            var botaoExcluir = $("<button>", {
                type: "button", class: "btn btn-danger", text: "Excluir"
            }).on("click", (function (indice) {
                return function () { excluir(indice); };
            })(i));

            linha.append($("<td>").append(botaoEditar));
            linha.append($("<td>").append(botaoExcluir));
            tbody.append(linha);
        }
    }

    var totalPaginas = Math.ceil((lista ? lista.length : 0) / itensPagina);
    $("#numeracao").text(
        "Página " + (totalPaginas > 0 ? contarPagina + 1 : 0) + " de " + totalPaginas
    );
}

// CONFIGURAR PAGINAÇÃO
function botoesConfig(lista) {
    var totalPaginas = Math.ceil((lista ? lista.length : 0) / itensPagina);
    $("#btnProximo").prop("disabled", totalPaginas === 0 || contarPagina >= totalPaginas - 1);
    $("#btnAnterior").prop("disabled", contarPagina <= 0);
}

$(document).ready(function () {
    $("#btnProximo").on("click", function () {
        var totalPaginas = Math.ceil(lstP.length / itensPagina);
        if (contarPagina < totalPaginas - 1) {
            contarPagina++;
            preencherTabela(lstP);
            botoesConfig(lstP);
        }
    });

    $("#btnAnterior").on("click", function () {
        if (contarPagina > 0) {
            contarPagina--;
            preencherTabela(lstP);
            botoesConfig(lstP);
        }
    });
});
