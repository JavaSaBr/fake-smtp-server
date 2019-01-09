package de.gessnerfl.fakesmtp.controller;

import de.gessnerfl.fakesmtp.model.ContentType;
import de.gessnerfl.fakesmtp.model.Email;
import de.gessnerfl.fakesmtp.model.EmailAttachment;
import de.gessnerfl.fakesmtp.model.EmailContent;
import de.gessnerfl.fakesmtp.repository.EmailRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("integrationtest")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerMVCIntegrationTest {

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        emailRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldReturnEmptyListWhenNoEmailsAreAvailable() throws Exception {
        this.mockMvc.perform(get("/email?page"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("mails", emptyIterableOf(Email.class)))
                .andExpect(view().name("email-list"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldReturnListOfEmailsPagedWhenEmailsAreAvailable() throws Exception {
        var email1 = createRandomEmail(5);
        var email2 = createRandomEmail(2);
        var email3 = createRandomEmail(1);

        this.mockMvc.perform(get("/email?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("mails", iterableWithSize(2)))
                .andExpect(model().attribute("mails", contains(equalTo(email3), equalTo(email2))))
                .andExpect(view().name("email-list"));

        this.mockMvc.perform(get("/email?page=1&size=2"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("mails", iterableWithSize(1)))
                .andExpect(model().attribute("mails", contains(equalTo(email1))))
                .andExpect(view().name("email-list"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldReturnFirstPageWhenGivenPageIsOutOfRange() throws Exception {
        createRandomEmail(1);

        this.mockMvc.perform(get("/email?page=1&size=2"))
                .andExpect(status().isFound())
                .andExpect(model().attributeDoesNotExist("mails"))
                .andExpect(redirectedUrl("/email"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldReturnMailById() throws Exception {
        var email = createRandomEmail(1);

        this.mockMvc.perform(get("/email/" + email.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("mail", equalTo(email)))
                .andExpect(view().name("email"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldReturnErrorWhenMailIdIsNotValid() throws Exception {
        this.mockMvc.perform(get("/email/123"))
                .andExpect(redirectedUrl("/email"))
                .andExpect(model().attributeDoesNotExist("mails", "mail"))
                .andExpect(status().isFound());
    }

    private Matcher<Email> equalsMail(Email email) {
        return new BaseMatcher<Email>() {
            @Override
            public boolean matches(Object item) {
                if (item instanceof Email) {
                    Email other = (Email) item;
                    if (email.getId() == other.getId()) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("equalsMail should return email with id ").appendValue(email.getId());
            }
        };
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldReturnAttachmentForEmail() throws Exception {
        var email = createRandomEmail(1);
        var attachment = email.getAttachments().get(0);

        this.mockMvc.perform(get("/email/"+email.getId()+"/attachment/" + attachment.getId()))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + attachment.getFilename()))
                .andExpect(header().string(HttpHeaders.CONTENT_LENGTH,"" + attachment.getData().length))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE))
                .andExpect(content().bytes(attachment.getData()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldReturnErrorWhenAttachmentIsRequestedButAttachmentIdIsNotValid() throws Exception {
        var email = createRandomEmail(1);

        this.mockMvc.perform(get("/email/"+email.getId()+"/attachment/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void shouldReturnErrorWhenAttachmentIsRequestedButMailIdIsNotValid() throws Exception {
        var email = createRandomEmail(1);

        this.mockMvc.perform(get("/email/123/attachment/"+email.getAttachments().get(0).getId()))
                .andExpect(status().isNotFound());
    }

    private Email createRandomEmail(int minusMinutes) {
        var randomToken = RandomStringUtils.randomAlphanumeric(6);
        var localDateTime = LocalDateTime.now().minusMinutes(minusMinutes);
        var receivedOn = Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());

        var content = new EmailContent();
        content.setContentType(ContentType.PLAIN);
        content.setData("Test Content "+randomToken);

        var attachment = new EmailAttachment();
        attachment.setFilename("test.txt");
        attachment.setData("This is some test data".getBytes(StandardCharsets.UTF_8));

        var mail = new Email();
        mail.setSubject("Test Subject "+randomToken);
        mail.setRawData("Test Content "+randomToken);
        mail.setReceivedOn(receivedOn);
        mail.setFromAddress("sender@example.com");
        mail.setToAddress("receiver@example.com");
        mail.addContent(content);
        mail.addAttachment(attachment);
        return emailRepository.save(mail);
    }

}
