package br.unisinos.encodedecodestepbystep.service.codification;

import br.unisinos.encodedecodestepbystep.domain.Codification;
import br.unisinos.encodedecodestepbystep.domain.ReaderWriterWrapper;
import br.unisinos.encodedecodestepbystep.repository.ReaderInterface;
import br.unisinos.encodedecodestepbystep.repository.WriterInterface;
import br.unisinos.encodedecodestepbystep.repository.codification.Writer;
import br.unisinos.encodedecodestepbystep.repository.redundancy.WriterRedundancy;
import br.unisinos.encodedecodestepbystep.utils.exceptions.WrongFormatExpection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static br.unisinos.encodedecodestepbystep.service.codification.AssertionsEncodeDecode.makeAssertions;
import static br.unisinos.encodedecodestepbystep.service.codification.SetUpWriterReader.setUpDecodeAlice29;
import static br.unisinos.encodedecodestepbystep.service.codification.SetUpWriterReader.setUpEncodeAlice29;

class UnarioServiceTest {

    WriterInterface writer;
    ReaderInterface reader;
    UnarioService unarioService;

    @BeforeEach
    void setUp() {
        this.unarioService = new UnarioService();
    }

    void setUp(ReaderWriterWrapper readerWriterWrapper) {
        this.writer = readerWriterWrapper.getWriterInterface();
        this.reader = readerWriterWrapper.getReaderInterface();
    }

    @Test
    void deveFicarIgualOArquivoOriginalQuandoExecutadoEncodeEAposDecodeDeUmTxt() throws IOException, WrongFormatExpection {
        Codification.setEncodeCodification(true);
        setUp(setUpEncodeAlice29());
        unarioService.encode(this.writer, this.reader);

        Codification.setEncodeCodification(false);
        setUp(setUpDecodeAlice29());
        unarioService.decode(this.writer, this.reader);

        makeAssertions("alice29.txt");
    }

//    @Test
//    void deveFicarIgualOArquivoOriginalQuandoExecutadoEncodeEAposDecodeDeUmExecutavel() throws IOException, WrongFormatExpection {
//        Codification.setEncodeCodification(true);
//        setUp(setUpEncodeSum());
//        unarioService.encode(this.writer, this.reader);
//
//        Codification.setEncodeCodification(false);
//        setUp(setUpDecodeSum());
//        unarioService.decode(this.writer, this.reader);
//
//        makeAssertions("sum");
//    }

//    @Test
//    void deveFicarIgualOArquivoOriginalQuandoExecutadoComTratamentoDeRuidoEncodeEAposDecodeDeUmTxt() throws IOException, WrongFormatExpection {
//        setUp(setUpEncodeWithRedundancyAlice29());
//        unario.encode(this.writer, this.reader);
//
//        setUp(setUpDecodeWithRedundancyAlice29());
//        unario.decode(this.writer, this.reader);
//
//        makeAssertions();
//    }
//
//    @Test
//    void deveFicarIgualOArquivoOriginalQuandoExecutadoComTratamentoDeRuidoEncodeEAposDecodeDeUmExecutavel() throws IOException, WrongFormatExpection {
//        setUp(setUpEncodeWithRedundancySum());
//        unario.encode(this.writer, this.reader);
//
//        setUp(setUpDecodeWithRedundancySum());
//        unario.decode(this.writer, this.reader);
//
//        makeAssertions();
//    }

    @Test
    void deveRetornarIdentificaoEmBitsDoAlgoritmoSemByteCRC8QuandoNaoUtilizadoTratamentoDeRuido() throws IOException {
        String bitsIdentificacaoAlgoritmoEsperado = "0001111100000000";
        String bitsIdentificacaoAlgoritmoRetornado = unarioService.getBitsIdentificacaoAlgoritmo(new Writer("src/test/resources/filesToEncodeDecodeTest/alice29.txt.cod"));
        Assertions.assertEquals(bitsIdentificacaoAlgoritmoEsperado, bitsIdentificacaoAlgoritmoRetornado);
    }

    @Test
    void deveRetornarIdentificaoEmBitsDoAlgoritmoComByteCRC8QuandoUtilizadoTratamentoDeRuido() throws IOException {
        String bitsIdentificacaoAlgoritmoEsperado = "000111110000000010010100";
        String bitsIdentificacaoAlgoritmoRetornado = unarioService.getBitsIdentificacaoAlgoritmo(new WriterRedundancy("src/test/resources/filesToEncodeDecodeTest/alice29.txt.cod"));
        Assertions.assertEquals(bitsIdentificacaoAlgoritmoEsperado, bitsIdentificacaoAlgoritmoRetornado);
    }
}