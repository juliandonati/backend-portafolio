package com.juliandonati.backendPortafolio.data;

import com.juliandonati.backendPortafolio.domain.*;
import com.juliandonati.backendPortafolio.exception.ResourceNotFoundException;
import com.juliandonati.backendPortafolio.security.domain.Role;
import com.juliandonati.backendPortafolio.security.domain.User;
import com.juliandonati.backendPortafolio.security.dto.RegisterRequestDto;
import com.juliandonati.backendPortafolio.security.service.RoleService;
import com.juliandonati.backendPortafolio.security.service.UserService;
import com.juliandonati.backendPortafolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final PortfolioService portfolioService;

    @Override
    public void run(String... args) throws Exception {
        Role userRole;
        try{
            userRole = roleService.findByName("ROLE_USER");
        }
        catch(ResourceNotFoundException ex){
            userRole = new Role();
            userRole.setName("ROLE_USER");
            userRole.setDescription("Rol genérico de usuario");
            roleService.save(userRole);
        }

        Role adminRole;
        try{
            adminRole = roleService.findByName("ROLE_ADMIN");
        }
        catch(ResourceNotFoundException ex){
            adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            adminRole.setDescription("Rol genérico de administrador");
            roleService.save(adminRole);
        }


        User adminTestUser;
        try{
            adminTestUser = userService.findByUsername("admin_test_user");
        }
        catch(ResourceNotFoundException ex){
            RegisterRequestDto requestDto = new RegisterRequestDto();
            requestDto.setUsername("admin");
            requestDto.setUnencryptedPassword("4ZeBBgAK5a8d");
            requestDto.setDisplayName("USER NAME");
            requestDto.setEmail("admin@example.com");

            Set<String> roles = new HashSet<>();
            roles.add("ROLE_ADMIN");
            roles.add("ROLE_USER");

            requestDto.setRoles(roles);

            adminTestUser = userService.register(requestDto);
        }

        Portfolio adminTestPortfolio;
        try{
            adminTestPortfolio = portfolioService.findByOwner(adminTestUser);
        }
        catch (ResourceNotFoundException ex){
            adminTestPortfolio = new Portfolio();

            Presentation presentation = new Presentation(null,"PRESENTATION NAME","PRESENTATION TITLE","PRESENTATION DESCRIPTION",null,adminTestPortfolio);
            adminTestPortfolio.setPresentation(presentation);

            AboutMe aboutMe = new AboutMe(null,"SOBRE MÍ","TEXTO SOBRE MÍ",null,null,null,adminTestPortfolio);
            adminTestPortfolio.setAboutMe(aboutMe);

            adminTestPortfolio.addDegree(new Degree(null,"TÍTULO ACADÉMICO 1","DESCRIPCIÓN DE TÍTULO ACADÉMICO", LocalDate.now(),null,null,adminTestPortfolio));

            adminTestPortfolio.addSkill(new Skill(null,"HABILIDAD 1","DESCRIPCIÓN DE HABILIDAD 1","NIVEL DE HABILIDAD 1",null,null,adminTestPortfolio));

            adminTestPortfolio.addExperience(new Job(null,"TRABAJO 1","POSICIÓN TRABAJO 1","DESCRIPCIÓN TRABAJO 1",LocalDate.now(),null,adminTestPortfolio));

            adminTestPortfolio.setOwner(adminTestUser);
            adminTestUser.addOwnedPortfolio(adminTestPortfolio);

            userService.save(adminTestUser); // Se vuelve a guardar el usuario de prueba, solo que ahora se le agrega el portfolio.
        }
    }

}
