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

class GoulombServiceTest {

    WriterInterface writer;
    ReaderInterface reader;
    GoulombService goulombService;

    @BeforeEach
    void setUp() {
        this.goulombService = new GoulombService(2);
    }

    void setUp(ReaderWriterWrapper readerWriterWrapper) {
        this.writer = readerWriterWrapper.getWriterInterface();
        this.reader = readerWriterWrapper.getReaderInterface();
    }

    @Test
    void deveFicarIgualOArquivoOriginalQuandoExecutadoEncodeEAposDecodeDeUmTxt() throws IOException, WrongFormatExpection {
        Codification.setEncodeCodification(true);
        setUp(setUpEncodeAlice29());
        goulombService.encode(this.writer, this.reader);

        Codification.setEncodeCodification(false);
        setUp(setUpDecodeAlice29());
        goulombService.decode(this.writer, this.reader);

        makeAssertions("alice29.txt");
    }

//    @Test
//    void deveFicarIgualOArquivoOriginalQuandoExecutadoEncodeEAposDecodeDeUmExecutavel() throws IOException, WrongFormatExpection {
//        Codification.setEncodeCodification(true);
//        setUp(setUpEncodeSum());
//        goulombService.encode(this.writer, this.reader);
//
//        Codification.setEncodeCodification(false);
//        setUp(setUpDecodeSum());
//        goulombService.decode(this.writer, this.reader);
//
//        makeAssertions("sum");
//    }

//    @Test
//    void deveFicarIgualOArquivoOriginalQuandoExecutadoComTratamentoDeRuidoEncodeEAposDecodeDeUmTxt() throws IOException, WrongFormatExpection {
//        setUp(setUpEncodeWithRedundancyAlice29());
//        goulomb.encode(this.writer, this.reader);
//
//        setUp(setUpDecodeWithRedundancyAlice29());
//        goulomb.decode(this.writer, this.reader);
//
//        makeAssertions();
//    }
//
//    @Test
//    void deveFicarIgualOArquivoOriginalQuandoExecutadoComTratamentoDeRuidoEncodeEAposDecodeDeUmExecutavel() throws IOException, WrongFormatExpection {
//        setUp(setUpEncodeWithRedundancySum());
//        goulomb.encode(this.writer, this.reader);
//
//        setUp(setUpDecodeWithRedundancySum());
//        goulomb.decode(this.writer, this.reader);
//
//        makeAssertions();
//    }

    @Test
    void deveRetornarIdentificaoEmBitsDoAlgoritmoComSegundoByteCorrepondendoAoTamanhoDoDivisor() throws IOException {
        goulombService = new GoulombService(21);
        String bitsIdentificacaoAlgoritmoEsperado = "0000111100010101";
        String bitsIdentificacaoAlgoritmoRetornado = goulombService.getBitsIdentificacaoAlgoritmo(new Writer("src/test/resources/filesToEncodeDecodeTest/alice29.txt.cod"));
        Assertions.assertEquals(bitsIdentificacaoAlgoritmoEsperado, bitsIdentificacaoAlgoritmoRetornado);
    }

    @Test
    void deveRetornarIdentificaoEmBitsDoAlgoritmoSemByteCRC8QuandoNaoUtilizadoTratamentoDeRuido() throws IOException {
        String bitsIdentificacaoAlgoritmoEsperado = "0000111100000010";
        String bitsIdentificacaoAlgoritmoRetornado = goulombService.getBitsIdentificacaoAlgoritmo(new Writer("src/test/resources/filesToEncodeDecodeTest/alice29.txt.cod"));
        Assertions.assertEquals(bitsIdentificacaoAlgoritmoEsperado, bitsIdentificacaoAlgoritmoRetornado);
    }

    @Test
    void deveRetornarIdentificaoEmBitsDoAlgoritmoComByteCRC8QuandoUtilizadoTratamentoDeRuido() throws IOException {
        String bitsIdentificacaoAlgoritmoEsperado = "000011110000001011001101";
        String bitsIdentificacaoAlgoritmoRetornado = goulombService.getBitsIdentificacaoAlgoritmo(new WriterRedundancy("src/test/resources/filesToEncodeDecodeTest/alice29.txt.cod"));
        Assertions.assertEquals(bitsIdentificacaoAlgoritmoEsperado, bitsIdentificacaoAlgoritmoRetornado);
    }
}