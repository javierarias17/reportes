package co.com.pragma.consumer.dto;

import java.util.List;

public record GetAdminEmailsOutDTO(
        List<String> lstAdminEmails
) {}