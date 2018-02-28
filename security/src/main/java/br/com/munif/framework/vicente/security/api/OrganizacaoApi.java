/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.security.domain.Organizacao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

/**
 *
 * @author GeradorVicente
 */
@RestController
@RequestMapping("/api/organizacao")
public class OrganizacaoApi extends BaseAPI<Organizacao> {

    private final Logger log = Logger.getLogger(OrganizacaoApi.class);

    private static final String ENTITY_NAME = "organizacao";

    public OrganizacaoApi(BaseService<Organizacao> service) {
        super(service);
    }
    

}
