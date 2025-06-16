package com.rslakra.vantageservice.task.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rslakra.appsuite.core.json.JSONUtils;

import java.util.List;

/**
 * Task Examples:
 * <pre>
 * [
 *     {
 *         "featureName": "Privacy",
 *         "title": "Handle New Privacy Laws in US - Effective Date",
 *         "priority": 1,
 *         "startDate": "2023-04-01",
 *         "endDate": "2023-04-30",
 *         "status": "INACTIVE",
 *         "description": "Add support for global privacy platform (GPP)",
 *         "link": "JIRA-0001 - Design doc",
 *         "notes": "Find dependencies and blockers"
 *     },
 *     {
 *         "featureName": "Privacy",
 *         "title": "Design Privacy Plug-in",
 *         "priority": 1,
 *         "startDate": "2023-04-01",
 *         "endDate": "2023-04-30",
 *         "status": "INACTIVE",
 *         "description": "Design Privacy Plug-in",
 *         "link": "JIRA-0002 - Design doc",
 *         "notes": "Find dependencies and blockers"
 *     },
 *     {
 *         "featureName": "AWS Migration",
 *         "title": "Migrate Core Services to AWS",
 *         "priority": 1,
 *         "startDate": "2023-04-01",
 *         "endDate": "2023-04-30",
 *         "status": "INACTIVE",
 *         "description": "Migrate Core Services to AWS",
 *         "link": "JIRA-0003 - Design doc",
 *         "notes": "Find dependencies and blockers"
 *     },
 *     {
 *         "featureName": "Commerce",
 *         "title": "ATS Migration for Proxy updates",
 *         "priority": 1,
 *         "startDate": "2023-04-01",
 *         "endDate": "2023-04-30",
 *         "status": "INACTIVE",
 *         "description": "ATS Migration for Proxy updates",
 *         "link": "JIRA-0004 - Design doc",
 *         "notes": "Find dependencies and blockers"
 *     }
 * ]
 * </pre>
 *
 * @author Rohtash Lakra
 * @created 1/2/24 9:50 AM
 */
public class TaskTest {


    public void testCreateTasks() {
        String jsonTasks = "[\n"
                           + "    {\n"
                           + "        \"featureName\": \"Privacy\",\n"
                           + "        \"title\": \"Handle New Privacy Laws in US - Effective Date\",\n"
                           + "        \"priority\": 1,\n"
                           + "        \"startDate\": \"2023-04-01\",\n"
                           + "        \"endDate\": \"2023-04-30\",\n"
                           + "        \"status\": \"INACTIVE\",\n"
                           + "        \"description\": \"Add support for global privacy platform (GPP)\",\n"
                           + "        \"link\": \"JIRA-0001 - Design doc\",\n"
                           + "        \"notes\": \"Find dependencies and blockers\"\n"
                           + "    },\n"
                           + "    {\n"
                           + "        \"featureName\": \"Privacy\",\n"
                           + "        \"title\": \"Design Privacy Plug-in\",\n"
                           + "        \"priority\": 1,\n"
                           + "        \"startDate\": \"2023-04-01\",\n"
                           + "        \"endDate\": \"2023-04-30\",\n"
                           + "        \"status\": \"INACTIVE\",\n"
                           + "        \"description\": \"Design Privacy Plug-in\",\n"
                           + "        \"link\": \"JIRA-0002 - Design doc\",\n"
                           + "        \"notes\": \"Find dependencies and blockers\"\n"
                           + "    },\n"
                           + "    {\n"
                           + "        \"featureName\": \"AWS Migration\",\n"
                           + "        \"title\": \"Migrate Core Services to AWS\",\n"
                           + "        \"priority\": 1,\n"
                           + "        \"startDate\": \"2023-04-01\",\n"
                           + "        \"endDate\": \"2023-04-30\",\n"
                           + "        \"status\": \"INACTIVE\",\n"
                           + "        \"description\": \"Migrate Core Services to AWS\",\n"
                           + "        \"link\": \"JIRA-0003 - Design doc\",\n"
                           + "        \"notes\": \"Find dependencies and blockers\"\n"
                           + "    },\n"
                           + "    {\n"
                           + "        \"featureName\": \"Commerce\",\n"
                           + "        \"title\": \"ATS Migration for Proxy updates\",\n"
                           + "        \"priority\": 1,\n"
                           + "        \"startDate\": \"2023-04-01\",\n"
                           + "        \"endDate\": \"2023-04-30\",\n"
                           + "        \"status\": \"INACTIVE\",\n"
                           + "        \"description\": \"ATS Migration for Proxy updates\",\n"
                           + "        \"link\": \"JIRA-0004 - Design doc\",\n"
                           + "        \"notes\": \"Find dependencies and blockers\"\n"
                           + "    }\n"
                           + "]";

        // create tasks from list
        List<Task> tasks = JSONUtils.fromJSONString(jsonTasks, List.class);
        assertEquals(4, tasks.size());
    }
}
