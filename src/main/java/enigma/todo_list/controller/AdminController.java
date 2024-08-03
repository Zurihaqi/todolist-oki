package enigma.todo_list.controller;

import enigma.todo_list.model.enums.Role;
import enigma.todo_list.service.serv.UserService;
import enigma.todo_list.utils.dto.RegisterRequestDTO;
import enigma.todo_list.utils.dto.UpdateRoleDTO;
import enigma.todo_list.utils.responseWrapper.PaginationResponse;
import enigma.todo_list.utils.responseWrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findAll(@PageableDefault Pageable pageable){
        return Response.renderJSON(new PaginationResponse<>(userService.getAll(pageable)));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return Response.renderJSON(userService.getById(id));
    }

    @PatchMapping("/users/{id}/role")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> updateRoleUser(@PathVariable Integer id, @RequestBody UpdateRoleDTO req){
        return new ResponseEntity<>(
                userService.updateRoleById(id, req),
                HttpStatus.OK
        );
    }

    @PostMapping("/super-admin")
    public ResponseEntity<?> createSuperAdmin(@RequestBody RegisterRequestDTO req){
        return new ResponseEntity<>(
                userService.createSuperAdmin(req),
                HttpStatus.CREATED
        );
    }
}
