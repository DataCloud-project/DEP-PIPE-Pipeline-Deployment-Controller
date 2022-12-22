package eu.datacloud.DEP-PIPE-translator;

/**
 * @author Konstantinos Theodosiou
 * @email konstheodosiou@gmail.com
 * @date 24/1/22
 */

@RestController
@RequestMapping("/api/v1/datacloud")
@SuppressWarnings("Duplicates")
public class DatacloudController {

    private static final Logger logger = Logger.getLogger(DatacloudController.class.getName());

    @Autowired
    private ComponentDAO componentDAO;
    @Autowired
    private RequirementDAO requirementDAO;

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private AuthService authService;

    @Autowired
    private DatacloudService datacloudService;

    @Autowired
    private DeploymentService deploymentService;

    //TODO: DatacloudStepTo is complete?
    @PostMapping
    public ResponseEntity<RestResponseSPA<Serializable>> createStep(@RequestBody DatacloudStepTo datacloudStepTo, HttpServletRequest request) {

        boolean requiredFields = null != datacloudStepTo
                && null != datacloudStepTo.getChunkName() && !datacloudStepTo.getChunkName().isEmpty()
                && null != datacloudStepTo.getPipelineName() && !datacloudStepTo.getPipelineName().isEmpty()
                && null != datacloudStepTo.getPipelineType() && !datacloudStepTo.getPipelineType().isEmpty()
                && null != datacloudStepTo.getTerminationCheck() && null != datacloudStepTo.getTerminationCheck().getHttpURL() && !datacloudStepTo.getTerminationCheck().getHttpURL().isEmpty()
                && null != datacloudStepTo.getStepsList() && !datacloudStepTo.getStepsList().isEmpty();

        if (requiredFields) {
            try {
                    /*
                    Create componentes and return
                    List of components and list of list of GraphLinks
                     */
                DatacloudService.Pair<List<Component>, List<ArrayList<GraphLink>>> pair = datacloudService.createStep(datacloudStepTo, authService.getAuthenticatedUser());
                //Retrive component with  RequiredInrerfaces
                pair = datacloudService.retriveReqInterf(pair,authService.getAuthenticatedUser());
                List<Component> components = (List<Component>) pair.a;
                List<ArrayList<GraphLink>> graphLinks = (List<ArrayList<GraphLink>>) pair.b;
                for(int i =0 ; i<components.size(); i++){
                    Component component = components.get(i);
                    ArrayList<GraphLink> listofGrapgLinks = graphLinks.get(i);
                    if(null != component && null != listofGrapgLinks){
                        component.setRequiredInterfaces(listofGrapgLinks);
                    }
                    //componentDAO.findById(component.getId()).get().getRequirement().getGpuRequired();
                    requirementDAO.findByComponent(component);
                }
                //update components with required interface
                datacloudService.updateWithReqInterfaces(pair,authService.getAuthenticatedUser());
                Application application= datacloudService.createApplication(datacloudStepTo,authService.getAuthenticatedUser(),pair);
                ApplicationInstance appInstance = datacloudService.createApplicationInstance(application,authService.getAuthenticatedUser());
                datacloudService.createDeployment(appInstance,authService.getAuthenticatedUser());

                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (NotAuthorizedException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestResponseSPA<>(ex.getMessage(),
                        ex.getGenericMessage().getMessage(request)));
            } catch (GenericBusinessException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponseSPA<>(ex.getMessage(),
                        ex.getGenericMessage().getMessage(request)));
            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponseSPA<>(GenericMessage.GENERIC_ERROR.getCode(),
                        GenericMessage.GENERIC_ERROR.getMessage(request)));
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponseSPA<>(GenericMessage.REQUIRED_FIELDS_MISSING.getCode(),
                    GenericMessage.REQUIRED_FIELDS_MISSING.getMessage(request)));
        }
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RestResponseSPA<Serializable>> deletePipeline(@PathVariable Long id, HttpServletRequest request) {
        boolean requiredFields = null != id && id != 0;
        if (requiredFields) {
            try {
                datacloudService.deletePipeline(id, authService.getAuthenticatedUser());
                return ResponseEntity.status(HttpStatus.OK).build();
            }catch (NotAuthorizedException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestResponseSPA<>(ex.getMessage(), ex.getGenericMessage().getMessage(request)));
            } catch (GenericBusinessException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponseSPA<>(ex.getMessage(),
                        ex.getGenericMessage().getMessage(request)));
            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponseSPA<>(GenericMessage.GENERIC_ERROR.getCode(),
                        GenericMessage.GENERIC_ERROR.getMessage(request)));
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponseSPA<>(GenericMessage.REQUIRED_FIELDS_MISSING.getCode(),
                    GenericMessage.REQUIRED_FIELDS_MISSING.getMessage(request)));
        }
    }
}
