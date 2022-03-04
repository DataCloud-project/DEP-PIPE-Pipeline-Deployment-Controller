package eu.datacloud.DEP-PIPE-translator;
/**
 * @author Konstantinos Theodosiou
 * @email konstheodosiou@gmail.com
 * @date 24/1/22
 */

@Service
@Transactional(rollbackOn = Exception.class)
public class DatacloudService {

    private static final Logger logger = Logger.getLogger(DatacloudService.class.getName());

    @Autowired
    ProviderDAO providerDao;

    @Autowired
    ComponentDAO componentDAO;

    @Autowired
    ApplicationDAO applicationDAO;

    @Autowired
    ComponentNodeInstanceDAO componentNodeInstanceDAO;

    @Autowired
    InterfaceService interfaceService;

    @Autowired
    ComponentNodeService componentNodeService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ApplicationInstanceService applicationInstanceService;

    @Autowired
    KubernetesService kubernetesService;

    @Autowired
    RequirementDAO requirementDAO;

    @Autowired
    ApplicationInstanceDAO applicationInstanceDAO;

    @Autowired
    DeploymentService deploymentService;

    @Autowired
    GraphLinkDAO graphLinkDAO;

    public Pair createStep(DatacloudStepTo datacloudStepTo, User authenticatedUser) {
        Pair pair = null;
        try {
            pair = createComponent(datacloudStepTo, authenticatedUser);
            //Application application = createApplication(datacloudStepTo, authenticatedUser, pair);
            //createApplicationInstance(application, authenticatedUser);
        } catch (GenericBusinessException e) {
            //TODO: Log something valid and return ?
            System.out.println("errpr");
        }
        //createApplicationInstance(application, authenticatedUser);
        //kubernetesService.deployment();
        //return false;
        return pair;
    }

    public Pair createComponent(DatacloudStepTo datacloudStepTo, User authenticatedUser) {
        List<JobTo> jobList = datacloudStepTo.getStepsList();
        //List<ComponentTo> jobList = datacloudStepTo.getJobList();
        List<Component> components = new ArrayList<>();
        //TODO make this list of list
        //List<GraphLink> graphLinks = new ArrayList<>();
        //List<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
        List<ArrayList<GraphLink>> listOfListGraLinks = new ArrayList<ArrayList<GraphLink>>();
        for (ComponentTo job : jobList) {
            if (null != job) {
                Component component = translateJobToComponent(job);
                List<GraphLink> graphLinkList = component.getRequiredInterfaces();
                component.setRequiredInterfaces(null);
                if (component != null) {
                    componentNodeService.create(component, authenticatedUser);
//                    component.setRequiredInterfaces(graphLinkList);
                    components.add(component);
                    listOfListGraLinks.add((ArrayList<GraphLink>) graphLinkList);
                }
                //TODO:Throw a valid error
                else {
                    System.out.println("component is null");
                }
            } else {
                throw new GenericBusinessException(GenericMessage.REQUIRED_FIELDS_MISSING.getCode(), GenericMessage.REQUIRED_FIELDS_MISSING);
            }

        }
        //TODO:CHeck nulls
//        for(Component component : components){
//            if(null != component && null != component.getRequiredInterfaces()){
//                for(GraphLink graphLink : component.getRequiredInterfaces()) {
//                    if(null != graphLink && null != graphLink.getInterfaceObj() && null != graphLink.getInterfaceObj().getName()) {
//                        String name = graphLink.getInterfaceObj().getName();
//                        Interface interf = interfaceService.fetchInterfaceByName(name);
//                       // List<Interface> interf = interfaceDAO.findAll();
//                        graphLink.setInterfaceObj(interf);
//                        //Optional<Interface> interface1 = interfaceDAO.findByName("mariaDBSQLInterfaceTest2");
//                    }
//                    else{
//                        System.out.println("throw a valid error");
//                    }
//                }
//                componentNodeService.updateWithInterfaces(component,authenticatedUser);
//            }
//        }
        return new Pair(components, listOfListGraLinks);
    }

    /**
     * Retrive requiredInterfaces for every componet
     *
     * @param pair              Pair of list of components, and list of list of Graphlinks
     * @param authenticatedUser
     * @return pair
     */
    public Pair retriveReqInterf(Pair pair, User authenticatedUser) {
        List<Component> components = (List<Component>) pair.a;
        List<ArrayList<GraphLink>> graphLinks = (List<ArrayList<GraphLink>>) pair.b;

        //TODO:check same size
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            ArrayList<GraphLink> listofGrapgLinks = graphLinks.get(i);
            if (null != component && null != listofGrapgLinks) {
                for (GraphLink graphLink : listofGrapgLinks) {
                    if (null != graphLink && null != graphLink.getInterfaceObj() && null != graphLink.getInterfaceObj().getName()) {
                        String name = graphLink.getInterfaceObj().getName();
                        //Reterive Interface
                        Interface interf = interfaceService.fetchInterfaceByName(name);
                        //Set GraphLink fields before insert to db
                        graphLink.setInterfaceObj(interf);
                        graphLink.setDateCreated(new Date());
                        graphLink.setLastModified(new Date());
                        graphLink.setComponent(component);
                    } else {
                        System.out.println("throw a valid error");
                    }
                    //
                    //component.setRequiredInterfaces(listofGrapgLinks);
                }
                // componentNodeService.updateWithInterfaces(component,authenticatedUser);
                // componentNodeService.updateWithInterfaces(component,authenticatedUser);
            }
            Optional<Component> component2 = componentDAO.findById(component.getId());
            requirementDAO.findByComponent(component);
            //componentDAO.findById(component.getId()).get().getRequirement().getGpu;

        }
        return new Pair(components, graphLinks);

    }

    /**
     * Update with components in db , with their ReqInterfaces
     *
     * @param pair              list of components, list of lists of components
     * @param authenticatedUser
     * @return
     */
    public Pair updateWithReqInterfaces(Pair pair, User authenticatedUser) {
        List<Component> components = (List<Component>) pair.a;
        List<ArrayList<GraphLink>> graphLinks = (List<ArrayList<GraphLink>>) pair.b;
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            ArrayList<GraphLink> listofGrapgLinks = graphLinks.get(i);
            if (null != component && null != listofGrapgLinks) {
                Component component2 = componentDAO.findById(component.getId()).get();
                component2.getRequirement();
                componentNodeService.updateWithInterfaces(component, authenticatedUser);
            }
        }
        return pair;
    }

    public Component translateJobToComponent(ComponentTo componentTo) {
        Component component = new Component();

        boolean componentExists = (null != componentTo);

        if (componentExists) {
            //component.setId(componentTo.getId());
            component.setName(componentTo.getName());
            //component.setHexID(componentTo.getHexID());
            component.setArchitecture(componentTo.getArchitecture());
            component.setElasticityControllerMode(componentTo.getElasticityControllerMode());
            component.setElasticityController(componentTo.getElasticityController());
            component.setDockerImage(componentTo.getDockerImage());
            component.setDockerRegistry(componentTo.getDockerRegistry());
            component.setDockerCredentialsUsing(componentTo.getDockerCredentialsUsing());
            component.setDockerCustomRegistry(componentTo.getDockerCustomRegistry());
            component.setDockerUsername(componentTo.getDockerUsername());
            component.setDockerPassword(componentTo.getDockerPassword());

            List<InterfaceTo> interfaceTos = componentTo.getExposedInterfaces();
            List<Interface> interfaces = translateListInterfaceToListInterface(interfaceTos);
            component.setExposedInterfaces(interfaces);

            List<GraphLinkTo> graphLinkTos = componentTo.getRequiredInterfaces();
            List<GraphLink> graphLinks = translateGraphLinkToGraphLink(graphLinkTos);
            component.setRequiredInterfaces(graphLinks);

            RequirementTo requirementTo = componentTo.getRequirement();
            Requirement requirement = translateRequirementToRequirement(requirementTo);
            component.setRequirement(requirement);

            HealthCheckTo healthCheckTo = componentTo.getHealthCheck();
            HealthCheck healthCheck = translateHealthCeckToHelthCheck(healthCheckTo);
            component.setHealthCheck(healthCheck);

            List<EnvironmentalVariableTo> environmentalVariableTos = componentTo.getEnvironmentalVariables();
            List<EnvironmentalVariable> environmentalVariables = translateListEnvToListEnv(environmentalVariableTos);
            component.setEnvironmentalVariables(environmentalVariables);

            List<DeviceTo> deviceTos = componentTo.getDevices();
            List<Device> deviceList = translateDevicesToDevices(deviceTos);
            component.setDevices(deviceList);

            List<VolumeTo> volumesTos = componentTo.getVolumes();
            List<Volume> volumeList = translateVolumesToVolumes(volumesTos);
            component.setVolumes(volumeList);

            SortedSet<LabelTo> labelTos = componentTo.getLabels();
            SortedSet<Label> labels = translateLabelsToLabels(labelTos);
            component.setLabels(labels);

            //TODO:check sortedset
            SortedSet<PluginTo> pluginTos = componentTo.getPlugins();
            SortedSet<Plugin> plugins = translatePluginTosPlugin(pluginTos);
            component.setPlugins(plugins);

            component.setCapabilityAdds(translateColCapabilityAdd(componentTo.getCapabilityAdds()));
            component.setCapabilityDrops(translateColCapabilityDrop(componentTo.getCapabilityDrops()));

            component.setPublicComponent(componentTo.getPublicComponent());
            component.setNetworkModeHost(componentTo.getNetworkModeHost());
            component.setPrivilege(componentTo.getPrivilege());
            component.setHostname(componentTo.getHostname());
            component.setSharedMemorySize(componentTo.getSharedMemorySize());
            component.setCommand(componentTo.getCommand());
            component.setUlimitMemlockSoft(componentTo.getUlimitMemlockSoft());
            component.setUlimitMemlockHard(componentTo.getUlimitMemlockHard());
            component.setDockerExecutionUser(componentTo.getDockerExecutionUser());
            component.setDateCreated(componentTo.getDateCreated());
            component.setLastModified(componentTo.getLastModified());
        } else {
            component = null;
        }
        ;
        return component;
    }

    public List<Interface> translateListInterfaceToListInterface(List<InterfaceTo> exposedInerfacesTo) {

        //boolean isListEmpty =  exposedInerfacesTo.isEmpty();
        List<Interface> exposedInterfaces = new ArrayList<>();
        if (null != exposedInerfacesTo && !exposedInerfacesTo.isEmpty()) {
            for (InterfaceTo expInterfaceTo : exposedInerfacesTo) {

                Interface expsedInterface = new Interface();
                //expsedInterface.setInterfaceID(expInterfaceTo.getInterfaceID());
                //expsedInterface.setDateCreated(new Date());
                //expsedInterface.setLastModified(new Date());
                expsedInterface.setName(expInterfaceTo.getName());
                expsedInterface.setPort(expInterfaceTo.getPort());
                expsedInterface.setInterfaceType(expInterfaceTo.getInterfaceType());
                expsedInterface.setTransmissionProtocol(expInterfaceTo.getTransmissionProtocol());
                exposedInterfaces.add(expsedInterface);
            }

        } else {
            exposedInterfaces = null;
        }
        return exposedInterfaces;
    }

    public List<GraphLink> translateGraphLinkToGraphLink(List<GraphLinkTo> graphLinkTos) {
        //boolean graphsisEmpty = graphLinkTos.isEmpty();
        List<GraphLink> graphLinks = new ArrayList<>();

        if (null != graphLinkTos && !graphLinkTos.isEmpty()) {
            for (GraphLinkTo graphLinkTo : graphLinkTos) {

                GraphLink graphLink = new GraphLink();
                //graphLink.setGraphLinkID(graphLinkTo.getGraphLinkID());
                //graphLink.setDateCreated(graphLinkTo.getDateCreated());
                //graphLink.setLastModified(graphLinkTo.getLastModified());
                graphLink.setFriendlyName(graphLinkTo.getFriendlyName());
                Interface interf = new Interface();
                InterfaceTo interfTo = graphLinkTo.getInterfaceObj();
                //TODO:Check if this should exist here
                interf.setInterfaceID(interfTo.getInterfaceID());
                interf.setInterfaceType(interfTo.getInterfaceType());
                interf.setName(interfTo.getName());
                interf.setPort(interfTo.getPort());
                interf.setVna(interfTo.getVna());
                interf.setTransmissionProtocol(interfTo.getTransmissionProtocol());
                interf.setLastModified(interfTo.getLastModified());
                interf.setDateCreated(interfTo.getDateCreated());
                graphLink.setInterfaceObj(interf);
                graphLinks.add(graphLink);
            }
        } else {
            graphLinks = null;
        }
        return graphLinks;
    }

    public Requirement translateRequirementToRequirement(RequirementTo requirementTo) {
        boolean reqirementExist = (null != requirementTo);
        Requirement requirement = new Requirement();
        if (reqirementExist) {
            //requirement.setRequirementID(requirementTo.getRequirementID());
            requirement.setGpuRequired(requirementTo.getGpuRequired());
            requirement.setDateCreated(requirementTo.getDateCreated());
            requirement.setLastModified(requirementTo.getLastModified());
            requirement.setHypervisorType(requirementTo.getHypervisorType());
            requirement.setRam(requirementTo.getRam());
            requirement.setStorage(requirementTo.getStorage());
            requirement.setvCPUs(requirementTo.getvCPUs());
        } else {
            requirement = null;
        }
        return requirement;
    }

    public HealthCheck translateHealthCeckToHelthCheck(HealthCheckTo healthCheckTo) {
        boolean healthCheckExists = (null != healthCheckTo);
        HealthCheck healthCheck = new HealthCheck();
        if (healthCheckExists) {
            //healthCheck.setHealthCheckID(healthCheckTo.getHealthCheckID());
            healthCheck.setDateCreated(healthCheckTo.getDateCreated());
            healthCheck.setName(healthCheckTo.getName());
            healthCheck.setLastModified(healthCheckTo.getLastModified());
            healthCheck.setArgs(healthCheckTo.getArgs());
            healthCheck.setHttpURL(healthCheckTo.getHttpURL());
            healthCheck.setInterval(healthCheckTo.getInterval());
        } else {
            healthCheck = null;
        }
        return healthCheck;
    }

    public List<EnvironmentalVariable> translateListEnvToListEnv(List<EnvironmentalVariableTo> environmentalVariablesTo) {
        //boolean isListEmpty =  environmentalVariablesTo.isEmpty();
        List<EnvironmentalVariable> environmentalVariables = new ArrayList<>();

        if (null != environmentalVariablesTo && !environmentalVariablesTo.isEmpty()) {
            for (EnvironmentalVariableTo environmentalVariableTo : environmentalVariablesTo) {

                EnvironmentalVariable environmentalVariable = new EnvironmentalVariable();
                //environmentalVariable.setEnvironmentalVariableID(environmentalVariableTo.getEnvironmentalVariableID());
                environmentalVariable.setDateCreated(environmentalVariableTo.getDateCreated());
                environmentalVariable.setValue(environmentalVariableTo.getValue());
                environmentalVariable.setKey(environmentalVariableTo.getKey());
                environmentalVariable.setLastModified(environmentalVariableTo.getLastModified());
                environmentalVariables.add(environmentalVariable);
            }

        } else {
            environmentalVariables = null;
        }
        return environmentalVariables;
    }

    public List<Device> translateDevicesToDevices(List<DeviceTo> devicesTos) {
        //boolean isListEmpty =  devicesTos.isEmpty();
        List<Device> devices = new ArrayList<>();

        if (null != devicesTos && !devicesTos.isEmpty()) {
            for (DeviceTo deviceTo : devicesTos) {

                Device device = new Device();
                //device.setDeviceID(deviceTo.getDeviceID());
                device.setDateCreated(deviceTo.getDateCreated());
                device.setValue(deviceTo.getValue());
                device.setLastModified(deviceTo.getLastModified());
                device.setKey(deviceTo.getKey());
                devices.add(device);
            }

        } else {
            devices = null;
        }
        return devices;
    }

    public List<Volume> translateVolumesToVolumes(List<VolumeTo> volumeTos) {
        //boolean isListEmpty =  volumeTos.isEmpty();
        List<Volume> volumes = new ArrayList<>();

        if (null != volumeTos && !volumeTos.isEmpty()) {
            for (VolumeTo volumeTo : volumeTos) {

                Volume volume = new Volume();
                //volume.setVolumeID(volumeTo.getVolumeID());
                volume.setDateCreated(volumeTo.getDateCreated());
                volumeTo.setDockerPath(volumeTo.getDockerPath());
                volumeTo.setFile(volumeTo.getFile());
                volumes.add(volume);
            }

        } else {
            volumes = null;
        }
        return volumes;
    }

    public SortedSet<Label> translateLabelsToLabels(SortedSet<LabelTo> labelTos) {
        //boolean isListEmpty =  labelTos.isEmpty();
        SortedSet<Label> labels = new TreeSet<>();

        if (null != labelTos && !labelTos.isEmpty()) {
            for (LabelTo labelTo : labelTos) {

                Label label = new Label();
                //device.setInterfaceID(expInterfaceTo.getInterfaceID());
                //label.setLabelID(labelTo.getLabelID());
                label.setDateCreated(labelTo.getDateCreated());
                label.setName(labelTo.getName());
                label.setLastModified(labelTo.getLastModified());
                labels.add(label);
            }

        } else {
            labels = null;
        }
        return labels;
    }

    public SortedSet<Plugin> translatePluginTosPlugin(SortedSet<PluginTo> pluginTos) {
        //boolean isListEmpty =  pluginTos.isEmpty();
        SortedSet<Plugin> plugins = new TreeSet<>();
        if (null != pluginTos && !pluginTos.isEmpty()) {
            for (PluginTo pluginTo : pluginTos) {

                Plugin plugin = new Plugin();
                plugin.setPluginID(pluginTo.getPluginID());
                plugin.setDateCreated(pluginTo.getDateCreated());
                plugin.setName(pluginTo.getName());
                plugin.setLastModified(pluginTo.getLastModified());
                plugin.setPluginType(pluginTo.getPluginType());
                plugin.setDownloadURL(pluginTo.getDownloadURL());
                plugin.setEndpoint(pluginTo.getEndpoint());
                List<Metric> metrics = translateListMetricToListMetric(pluginTo.getMetrics());
                plugin.setMetrics(metrics);
                plugin.setPort(pluginTo.getPort());
                plugin.setPublicPlugin(pluginTo.getPublicPlugin());
                User user = translateUserToUser(pluginTo.getUser());
                plugin.setUser(user);
                Organization org = translateOrganizationToOrganization(pluginTo.getOrganization());
                plugin.setOrganization(org);
                plugin.setModuleName(pluginTo.getModuleName());
                plugin.setMetricsCounter(pluginTo.getMetricsCounter());
                plugin.setImmutablePlugin(pluginTo.getImmutablePlugin());
                plugin.setDefaultPlugin(pluginTo.getDefaultPlugin());
                plugins.add(plugin);
            }

        } else {
            plugins = null;
        }
        return plugins;
    }

    public Metric translateMetricToMetric(MetricTo metricTo) {
        Metric metric = new Metric();
        if (null != metricTo) {
            //metric.setMetricID(metricTo.getMetricID());
            metric.setDateCreated(metricTo.getDateCreated());
            metric.setFriendlyName(metricTo.getFriendlyName());
            metric.setUnit(metricTo.getUnit());
            metric.setLastModified(metricTo.getLastModified());
            metric.setName(metricTo.getName());
        } else {
            metric = null;
        }
        return metric;
    }

    public User translateUserToUser(UserTo userTo) {
        User user = new User();
        if (null != userTo) {
            Country country = translateCountryToCountry(userTo.getCountry());
            user.setCountry(country);
            user.setVerifyPassword(userTo.getVerifyPassword());
            user.setUsername(userTo.getUsername());
            user.setRole(userTo.getRole());
            user.setPhone(userTo.getPhone());
            user.setPassword(userTo.getPassword());
            Organization org = translateOrganizationToOrganization(userTo.getOrganization());
            user.setOrganization(org);
            user.setNotificationWebEnabled(userTo.getNotificationWebEnabled());
            user.setNewPassword(userTo.getNewPassword());
            user.setLastName(userTo.getLastName());
            user.setNotificationEmailEnabled(userTo.getNotificationEmailEnabled());
            //user.setId(userTo.getId());
            user.setFirstName(userTo.getFirstName());
            user.setFirstLogin(userTo.getFirstLogin());
            user.setEnabled(userTo.getEnabled());
            user.setEmail(userTo.getEmail());
            user.setDateCreated(userTo.getDateCreated());
            user.setCurrentPassword(userTo.getCurrentPassword());
        } else {
            user = null;
        }
        return user;
    }

    public Country translateCountryToCountry(CountryTo countryTo) {
        Country country = new Country();
        if (null != countryTo) {
            country.setCountryCode(countryTo.getCountryCode());
            country.setSubRegionCode(countryTo.getSubRegionCode());
            country.setRegionCode(countryTo.getRegionCode());
            country.setIso3166(countryTo.getIso3166());
            country.setAlpha3(countryTo.getAlpha3());
            country.setAlpha2(countryTo.getAlpha2());
            country.setSubRegion(countryTo.getSubRegion());
            country.setSubRegionCode(countryTo.getSubRegionCode());
            country.setRegion(countryTo.getRegion());
            country.setRegionCode(countryTo.getRegionCode());
            country.setName(countryTo.getName());
        }
        return country;

    }

    public Organization translateOrganizationToOrganization(OrganizationTo organizationTo) {
        Organization organization = new Organization();
        if (null != organizationTo) {
            organization.setDateCreated(organizationTo.getDateCreated());
            //organization.setId(organizationTo.getId());
            organization.setLastModified(organizationTo.getLastModified());
            organization.setStatus(organizationTo.getStatus());
        } else {
            organization = null;
        }
        return organization;
    }

    public List<Metric> translateListMetricToListMetric(List<MetricTo> listMetricTo) {

        List<Metric> listMetric = new ArrayList<>();
        if (null != listMetricTo && !listMetricTo.isEmpty()) {
            for (MetricTo metricTo : listMetricTo) {
                Metric metric = translateMetricToMetric(metricTo);
                listMetric.add(metric);
            }
        } else {
            listMetric = null;
        }
        return listMetric;
    }

    public Collection<Component.CapabilityAdd> translateColCapabilityAdd
            (Collection<ComponentTo.CapabilityAdd> capabilitysTo) {

        //boolean isListEmpty =  capabilitysTo.isEmpty();
        Collection<Component.CapabilityAdd> capabilitys = new ArrayList<>();
        if (null != capabilitysTo && capabilitysTo.isEmpty()) {
            for (ComponentTo.CapabilityAdd capabilityAddTo : capabilitysTo) {
                Component.CapabilityAdd stuff = Component.CapabilityAdd.valueOf(capabilityAddTo.getFriendlyName());
                capabilitys.add(stuff);
            }
        } else {
            capabilitys = null;
        }
        return capabilitys;
    }

    public Collection<Component.CapabilityDrop> translateColCapabilityDrop
            (Collection<ComponentTo.CapabilityDrop> capabilitysTo) {

        //boolean isListEmpty = capabilitysTo.isEmpty();
        Collection<Component.CapabilityDrop> capabilitys = new ArrayList<>();

        if (null != capabilitysTo && !capabilitysTo.isEmpty()) {
            for (ComponentTo.CapabilityDrop capabilityDropTo : capabilitysTo) {
                Component.CapabilityDrop stuff = Component.CapabilityDrop.valueOf(capabilityDropTo.getFriendlyName());
                capabilitys.add(stuff);
            }
        } else {
            capabilitys = null;
        }
        return capabilitys;
    }

    public Application createApplication(DatacloudStepTo datacloudStepTo, User authenticatedUser, Pair pair) {
        List<Component> components = (List<Component>) pair.a;
        List<ArrayList<GraphLink>> graphLinks = (List<ArrayList<GraphLink>>) pair.b;
        Application application = translateStepToApplication(datacloudStepTo, components, graphLinks);
        applicationService.create(application, authenticatedUser);

        return application;
    }

    public void createDeployment(ApplicationInstance appInstance, User authenticatedUser) {
        Optional<Provider> datacloud = providerDao.findByName("https://datacloud-dep.euprojects.net/resource/5");
        Provider datacloudProvider = null;
        if (null != datacloud && datacloud.isPresent()) {
            datacloudProvider = datacloud.get();
        } else {
            //TODO: thorw a valid error
            logger.log(Level.SEVERE, "something went wrong");
        }
        appInstance.setProvider(datacloudProvider);

        deploymentService.requestDeployment(appInstance.getApplicationInstanceID(),
                authenticatedUser);

    }

    public Application translateStepToApplication(DatacloudStepTo datacloudStepTo, List<Component> components, List<ArrayList<GraphLink>> graphLinksList) {

        Application application = new Application();

        application.setName(datacloudStepTo.getChunkName());
        List<GraphLinkNode> graphLinkNodes = new ArrayList<>();
        List<ComponentNode> componentNodes = new ArrayList<>();
        Boolean componentExist = (null != components && !components.isEmpty());
        Boolean graphLinksListExist = (null != graphLinksList && !graphLinksList.isEmpty());
        /*
        Check if same size
        */
        if (componentExist && graphLinksListExist) {
            if (components.size() != graphLinksList.size()) {
                //TODO:Throw a valid error
                System.out.println("error");
            }
        }


        /*
        Collect componentNodes and Graphlinks
         */
        //for (ComponentTo componentTo : componentToList) {
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            ArrayList<GraphLink> graphLinks = graphLinksList.get(i);
            if (null != component) {
                ComponentNode componentNode = new ComponentNode();
                componentNode.setComponent(component);
                //componentNode.setApplication(application);
                componentNode.setName(component.getName());
                //componentNode.setComponentNodeID(component.getId());
                //TODO:Check this
                // componentNode.setLastModified(component.getLastModified());
                //componentNode.setGraphNodeID();
                //TODO:Check this
                // componentNode.setDateCreated(component.getDateCreated());
                //componentNode.setComponentName(component.getName());
                componentNodes.add(componentNode);

                if (null != graphLinks && !graphLinks.isEmpty()) {
                    //Set GraphLinks
                    for (GraphLink graphLink : graphLinks) {
                        GraphLinkNode graphLinkNode = new GraphLinkNode();
                        //graphLinkNode.setApplication(application);
                        graphLinkNode.setComponentNodeFrom(componentNode);
                        /*
                        Set component nodeTo
                         */
                        ComponentNode componentNodeTo = new ComponentNode();
                        Component componentTo = graphLink.getInterfaceObj().getComponent();
                        componentNodeTo.setComponent(componentTo);
                        componentNodeTo.setName(componentTo.getName());

                        graphLinkNode.setComponentNodeTo(componentNodeTo);
                        graphLinkNode.setGraphLink(graphLink);
                        graphLinkNodes.add(graphLinkNode);
                        // graphLinkNode.setGraphLinkNodeID();
                        //TODO:Check this
                        //graphLinkNode.setDateCreated();
                        //graphLinkNode.setLastModified();
                    }
                }
                //Graphlinks dont exit
                else {
                    //System.out.println("no graphlinks");
                }
            }
            //TODO:Throw a valid error
            else {
                System.out.println("throw a valid error");
            }
        }

        //TODO:sotre graphlinknodes

        //TODO:Checj if this is all needed
        application.setGraphLinkNodes(graphLinkNodes);
        application.setComponentNodes(componentNodes);

        //  TODO: COMPLETE THOSE APPROPRIATELY
        //application.setComponentNodes(datacloudStepTo.getJobList());
        //application.setDateCreated();
        //application.setUser();
        //application.setOrganization(Auth);
        //application.setName();
        //application.setLastModified();
        //application.setHexID();

        application.setPublicApplication(Boolean.TRUE);

        return application;
    }

    public ApplicationInstance createApplicationInstance(Application application, User authenticatedUser) {

        ApplicationInstance applicationInstance = new ApplicationInstance();

        //TODO: check it
        applicationInstance.setApplication(application);
        applicationInstance.setApplicationName(application.getName() + "Instance");
        applicationInstance.setName(application.getName() + "Instance");

        applicationInstanceService.create(applicationInstance, authenticatedUser);

        return applicationInstance;
    }

    // TODO upon createStep completion.

    public boolean deletePipeline(Long applicationInstanceId, User authenticatedUser) {
        //deploymentService.requestUndeployment(applicationInstanceId, authenticatedUser);
        Optional<ApplicationInstance> applicationInstanceOp = applicationInstanceDAO.findById(applicationInstanceId);
        ApplicationInstance applicationInstance = null;
        if (applicationInstanceOp.isPresent()) {
            applicationInstance = applicationInstanceOp.get();
        } else {
            logger.log(Level.SEVERE, "No applicationInstance");
        }
        //GetComponentNodes
        List<ComponentNodeInstance> componentNodesInstances = componentNodeInstanceDAO.findAllByApplicationInstance(applicationInstance);
        List<ComponentNode> components = findComponents(componentNodesInstances);

        Long applicationId = null;
        if (null != applicationInstance.getApplication()) {
            applicationId = applicationInstance.getApplication().getId();
        } else {
            logger.log(Level.SEVERE, "No applicationId");
        }

        //TODO:ist this equivelant with the above
        deleteApplicationInstance(applicationInstanceId, authenticatedUser);

        Application application = null;
        Optional<Application> applicationOp = applicationDAO.findById(applicationId);
        if (applicationOp.isPresent()) {
            application = applicationOp.get();
        } else {
            logger.log(Level.SEVERE, "No application");
        }
        List<Entry<Long, Integer>> importanceList = findComponentImportance(application);
        deleteApplication(applicationId, authenticatedUser);

        //TODO:fix deletion of component nodes
        Long componentId = null;
        //  for(Entry<Long,Integer> entry : importanceList){
        //    deleteComponent(entry.getKey(),authenticatedUser);
        // }

        //TODO:set flag
        int removedNodes = 0;
        while(removedNodes != components.size()){
            //while (!components.isEmpty()) {

            for (ComponentNode componentNode : components) {
                //if(component.)
                Component component = componentNode.getComponent();
                Long exposedInterfacesUsed = component.getExposedInterfaces().stream()
                        .filter(interfaceObj -> graphLinkDAO.countAllByInterfaceObj(interfaceObj) > 0).count();

                if (exposedInterfacesUsed <= 0  && null != componentNodeService.fetchComponentById(componentNode.getComponent().getId())) {
                    componentId = componentNode.getComponent().getId();
                    deleteComponent(componentId, authenticatedUser);
                    //components.remove(componentNode);
                    removedNodes++;
                }
            }
        }

        return true;
    }

    public boolean deleteApplicationInstance(Long applicationInstanceId, User authenticatedUser) {
        deploymentService.requestUndeployment(applicationInstanceId, authenticatedUser);
        return true;
    }

    public boolean deleteApplication(Long applicationId, User authenticateUser) {
        applicationService.delete(applicationId, authenticateUser);
        return true;
    }

    public boolean deleteComponent(Long componentId, User user) {
        componentNodeService.delete(componentId, user);
        return true;
    }

    public List<ComponentNode> findComponents(List<ComponentNodeInstance> componentNodeInstances) {
        ArrayList<ComponentNode> components = new ArrayList<>();
        if (null != componentNodeInstances && !componentNodeInstances.isEmpty()) {
            for (ComponentNodeInstance componentNodeInstance : componentNodeInstances) {
                ComponentNode component = componentNodeInstance.getComponentNode();
                components.add(component);
            }
        }
        return components;
    }


    public List<Entry<Long, Integer>> findComponentImportance(Application app) {

        List<GraphLinkNode> graphLinkNodes = app.getGraphLinkNodes();
        HashMap<Long, Integer> count = new HashMap<>();
        Long graphLinkId;
        for (GraphLinkNode graphLinkNode : graphLinkNodes) {
            graphLinkId = graphLinkNode.getComponentNodeFrom().getComponentNodeID();
            if (null != count.get(graphLinkId)) {
                count.put(graphLinkId, count.get(graphLinkId) + 1);
            } else {
                count.put(graphLinkId, 1);
            }
        }

        List<Entry<Long, Integer>> list = new ArrayList<>(count.entrySet());
        list.sort(Entry.comparingByValue());
        //Reverse List
        Collections.reverse(list);

        return list;
    }

    public class Pair<A, B> {

        public final List<Component> a;
        public final List<ArrayList<GraphLink>> b;

        public Pair(List<Component> a, List<ArrayList<GraphLink>> b) {
            this.a = a;
            this.b = b;
        }
    }


}
