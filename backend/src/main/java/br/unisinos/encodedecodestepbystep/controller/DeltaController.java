package br.unisinos.encodedecodestepbystep.controller;

import br.unisinos.encodedecodestepbystep.domain.Codification;
import br.unisinos.encodedecodestepbystep.domain.ReaderWriterWrapper;
import br.unisinos.encodedecodestepbystep.service.codification.DeltaService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/delta")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeltaController implements CodificationController {

    private final DeltaService deltaService;

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/normal/encode")
    @ResponseStatus(HttpStatus.OK)
    public void encode(@RequestBody String path) {
        Codification.setStepsFinished(false);
        Codification.setEncodeCodification(true);
        new Thread(() -> {
            try {
                Codification.setProgressPercentage(new MutableDouble(0));

                ReaderWriterWrapper readerWriterWrapper = ReaderWriterWrapper.getEncodeReaderWriterWrapperNormal(path, Codification.getProgressPercentage());
                deltaService.encode(readerWriterWrapper.getWriterInterface(), readerWriterWrapper.getReaderInterface());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
