/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
 /* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.security.domain.Grupo;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GeradorVicente
 */
@RestController
@RequestMapping("/api/grupo")
public class GrupoApi extends BaseAPI<Grupo> {

    private final Logger log = Logger.getLogger(GrupoApi.class.getSimpleName());

    private static final String ENTITY_NAME = "grupo";

    public GrupoApi(BaseService<Grupo> service) {
        super(service);
    }

}
