package io.github.managementsystem.managementsystem;

import io.github.managementsystem.managementsystem.Courses.CourseRepository;
import io.github.managementsystem.managementsystem.Courses.CourseStudentMappingRepository;
import io.github.managementsystem.managementsystem.Enclosures.EnclosureRepository;
import io.github.managementsystem.managementsystem.Files.FileRepository;
import io.github.managementsystem.managementsystem.Mappings.CourseSubjectMappingRepository_;
import io.github.managementsystem.managementsystem.Roles.RoleRepository;
import io.github.managementsystem.managementsystem.Roles.UserRoleMappingRepository;
import io.github.managementsystem.managementsystem.Students.StudentRepository;
import io.github.managementsystem.managementsystem.Subjects.CourseSubjectMapping;
import io.github.managementsystem.managementsystem.Subjects.SubjectRepository;
import io.github.managementsystem.managementsystem.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ManagementSystemApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleMappingRepository userRoleMappingRepository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CourseStudentMappingRepository courseStudentMappingRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private CourseSubjectMappingRepository_ courseSubjectMappingRepository;

	@Autowired
	private EnclosureRepository enclosureRepository;

	@Value("${admin.username}")
	private String username;

	@Value("${admin.password}")
	private String password;

	public static void main(String[] args) {
		SpringApplication.run(ManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.createTable();
		userRepository.createAdminUser(username, passwordEncoder.encode(password));

		roleRepository.createTable();
		roleRepository.createRoles();

		userRoleMappingRepository.createTable();
		userRoleMappingRepository.createAdminUser();

		studentRepository.createTable();
		enclosureRepository.createTable();
		fileRepository.createTable();

		courseRepository.createTable();
		courseStudentMappingRepository.createTable();

		subjectRepository.createTable();
		courseSubjectMappingRepository.createTable();
	}
}
