var itensPagina = 4;
var contarPagina = 0;
var lstI = [];
var lstPlantas = [];
var conteudoCampos = null;

var serv_porta = ":8084";
var serv_local = (window.location.hostname || "localhost") + serv_porta;
var servico = ["flores", "plantas", "classificar"];

function obterUrl() {
    return "http://" + serv_local + "/api/sistemas/" + servico[0];
}

function obterMedidas() {
    var campos = [
        { id: "#txtCompSepala", nome: "Comprimento da sépala" },
        { id: "#txtLarguraSepala", nome: "Largura da sépala" },
        { id: "#txtCompPepala", nome: "Comprimento da pétala" },
        { id: "#txtLarguraPepala", nome: "Largura da pétala" }
    ];
    var valores = [];

    for (var i = 0; i < campos.length; i++) {
        var texto = $(campos[i].id).val();
        if (texto === "" || texto === null || !Number.isFinite(Number(texto))) {
            alert("Informe um valor numérico válido para: " + campos[i].nome + ".");
            $(campos[i].id).focus();
            return null;
        }
        valores.push(Number(texto));
    }
    return valores;
}

// SALVAR OU ATUALIZAR
function salvar() {
    var medidas = obterMedidas();
    if (medidas === null) return;

    var cor = $("#txtCor").val().trim();
    var especieTipo = $("#txtTipoEspecie").val().trim();
    var idPlanta = $("#selPlanta").val();

    if (cor === "") {
        alert("Informe a cor da flor.");
        $("#txtCor").focus();
        return;
    }
    if (especieTipo === "") {
        alert("Informe o tipo da espécie.");
        $("#txtTipoEspecie").focus();
        return;
    }
    if (idPlanta === "" || idPlanta === null) {
        alert("Selecione uma planta.");
        $("#selPlanta").focus();
        return;
    }

    var obj_Flor = {
        comprimento_sepala: medidas[0],
        largura_sepala: medidas[1],
        comprimento_petala: medidas[2],
        largura_petala: medidas[3],
        cor: cor,
        especieTipo: especieTipo,
        idPlanta: Number(idPlanta)
    };

    if ($("#txtId").length > 0) {
        obj_Flor.idFlor = Number($("#txtId").val());
    }

    var xmlHTTP_POST = new XMLHttpRequest();
    xmlHTTP_POST.open("POST", obterUrl(), false);
    xmlHTTP_POST.setRequestHeader("Content-Type", "application/json");
    xmlHTTP_POST.send(JSON.stringify(obj_Flor));

    if (xmlHTTP_POST.status === 200 || xmlHTTP_POST.status === 201) {
        novo();
        consultar();
    } else {
        alert("Erro ao salvar flor: " + xmlHTTP_POST.status + "\n" + xmlHTTP_POST.responseText);
    }
}

// PREENCHER COMBO DE PLANTAS
function preencherCombo() {
    var xmlHTTP_GET = new XMLHttpRequest();
    xmlHTTP_GET.open("GET", "http://" + serv_local + "/api/sistemas/" + servico[1], false);
    xmlHTTP_GET.send();

    if (xmlHTTP_GET.status === 200) {
        try {
            lstPlantas = JSON.parse(xmlHTTP_GET.responseText) || [];
            var combo = $("#selPlanta");
            combo.empty();
            combo.append($("<option>", { value: "", text: "Escolha uma planta" }));

            for (var i = 0; i < lstPlantas.length; i++) {
                combo.append(
                    $("<option>", {
                        value: lstPlantas[i].idplanta,
                        text: lstPlantas[i].genero
                    })
                );
            }
        } catch (erro) {
            alert("A resposta da API de plantas não está em um formato JSON válido.");
            console.error(erro);
        }
    } else {
        $("#selPlanta").empty().append(
            $("<option>", { value: "", text: "Não foi possível carregar as plantas" })
        );
        alert("Erro ao carregar plantas: " + xmlHTTP_GET.status + "\n" + xmlHTTP_GET.responseText);
    }
}

// EDITAR
function editar(indexItem) {
    if (!lstI[indexItem]) return;

    if ($("#txtId").length === 0) {
        conteudoCampos = $("#compoId").html();
        $("#compoId").prepend(
            '<div class="col-sm-3" id="campoIdFlor">' +
            '<label for="txtId">ID:</label>' +
            '<input type="text" class="form-control" id="txtId" name="txtId" disabled>' +
            '</div>'
        );
    }

    var flor = lstI[indexItem];
    $("#txtId").val(flor.idflor);
    $("#txtCompSepala").val(flor.comprimento_sepala);
    $("#txtLarguraSepala").val(flor.largura_sepala);
    $("#txtCompPepala").val(flor.comprimento_pepala);
    $("#txtLarguraPepala").val(flor.largura_petala);
    $("#txtCor").val(flor.cor);
    $("#txtTipoEspecie").val(flor.especieTipo);
    $("#selPlanta").val(flor.planta ? flor.planta.idplanta : "");
    $("#btnNovo").prop("disabled", false);
}

// EXCLUIR
function excluir(indexItem) {
    if (!lstI[indexItem]) return;
    if (!confirm("Deseja realmente excluir esta flor?")) return;

    var obj_Flor = { idflor: lstI[indexItem].idflor };
    var xmlHTTP_DELETE = new XMLHttpRequest();
    xmlHTTP_DELETE.open("DELETE", obterUrl(), false);
    xmlHTTP_DELETE.setRequestHeader("Content-Type", "application/json");
    xmlHTTP_DELETE.send(JSON.stringify(obj_Flor));

    if (xmlHTTP_DELETE.status === 200 || xmlHTTP_DELETE.status === 204) {
        novo();
        consultar();
    } else {
        alert("Erro ao excluir flor: " + xmlHTTP_DELETE.status + "\n" + xmlHTTP_DELETE.responseText);
    }
}

// LIMPAR FORMULÁRIO / NOVO CADASTRO
function novo() {
    if ($("#txtId").length > 0) {
        $("#compoId").html(conteudoCampos);
    }

    $("#txtCompSepala").val("");
    $("#txtLarguraSepala").val("");
    $("#txtCompPepala").val("");
    $("#txtLarguraPepala").val("");
    $("#txtCor").val("");
    $("#txtTipoEspecie").val("");
    $("#selPlanta").prop("selectedIndex", 0);
    $("#selClassificador").prop("selectedIndex", 0);
    $("#LblSugestao").empty();
    $("#btnNovo").prop("disabled", true);
}

// CONSULTAR
function consultar() {
    var xmlHTTP_GET = new XMLHttpRequest();
    xmlHTTP_GET.open("GET", obterUrl(), false);
    xmlHTTP_GET.send();

    if (xmlHTTP_GET.status === 200) {
        try {
            lstI = JSON.parse(xmlHTTP_GET.responseText) || [];
            contarPagina = 0;
            preencherTabela(lstI);
            botoesConfig(lstI);
        } catch (erro) {
            alert("A resposta da API de flores não está em um formato JSON válido.");
            console.error(erro);
        }
    } else {
        alert("Erro ao consultar flores: " + xmlHTTP_GET.status + "\n" + xmlHTTP_GET.responseText);
    }
}

// PREENCHER TABELA
function preencherTabela(lista) {
    var tbody = $("#tabItens tbody");
    tbody.empty();

    if (!lista || lista.length === 0) {
        tbody.append(
            $("<tr>").append(
                $("<td>", { colspan: 9, class: "text-center" }).text("Nenhuma flor cadastrada.")
            )
        );
    } else {
        for (var i = contarPagina * itensPagina;
            i < lista.length && i < (contarPagina + 1) * itensPagina; i++) {
            var flor = lista[i];
            var linha = $("<tr>");
            linha.append($("<td>").text(flor.comprimento_sepala ?? ""));
            linha.append($("<td>").text(flor.largura_sepala ?? ""));
            linha.append($("<td>").text(flor.comprimento_pepala ?? ""));
            linha.append($("<td>").text(flor.largura_petala ?? ""));
            linha.append($("<td>").text(flor.cor ?? ""));
            linha.append($("<td>").text(flor.planta ? flor.planta.genero : ""));
            linha.append($("<td>").text(flor.especieTipo ?? ""));

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

// CLASSIFICAR FLOR
function classificarFlor() {
    $("#LblSugestao").empty();
    var classificador = $("#selClassificador").val();
    if (!classificador || classificador === "ESCOLHA uma opção") return;

    var medidas = obterMedidas();
    if (medidas === null) {
        $("#selClassificador").prop("selectedIndex", 0);
        return;
    }

    var obj_Classificar = {
        comprimento_sepala: medidas[0],
        largura_sepala: medidas[1],
        comprimento_petala: medidas[2],
        largura_petala: medidas[3],
        classifica: classificador
    };

    var xmlHTTP_POST = new XMLHttpRequest();
    xmlHTTP_POST.open("POST", "http://" + serv_local + "/api/sistemas/" + servico[2], false);
    xmlHTTP_POST.setRequestHeader("Content-Type", "application/json");
    xmlHTTP_POST.send(JSON.stringify(obj_Classificar));

    if (xmlHTTP_POST.status === 200) {
        try {
            var resultado = JSON.parse(xmlHTTP_POST.responseText);
            $("#LblSugestao").text(resultado.classe ?? "Classificação não retornada.");
        } catch (erro) {
            alert("A resposta da classificação não está em um formato JSON válido.");
            console.error(erro);
        }
    } else {
        alert("Erro ao classificar flor: " + xmlHTTP_POST.status + "\n" + xmlHTTP_POST.responseText);
    }
}

// EVENTOS DE PAGINAÇÃO
$(document).ready(function () {
    $("#btnProximo").on("click", function () {
        var totalPaginas = Math.ceil(lstI.length / itensPagina);
        if (contarPagina < totalPaginas - 1) {
            contarPagina++;
            preencherTabela(lstI);
            botoesConfig(lstI);
        }
    });

    $("#btnAnterior").on("click", function () {
        if (contarPagina > 0) {
            contarPagina--;
            preencherTabela(lstI);
            botoesConfig(lstI);
        }
    });
});
