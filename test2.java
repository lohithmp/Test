org.apache.jasper.JasperException: Unable to compile class for JSP: 

An error occurred at line: [59] in the jsp file: [/txnTrack.jsp]
The type com.fasterxml.jackson.core.JsonParser cannot be resolved. It is indirectly referenced from required .class files
56:             }
57: 
58:             ObjectMapper mapper = new ObjectMapper();
59:             Map data = mapper.readValue(apiResponse, Map.class);
60: 
61: %>
62:             <div class="box">


An error occurred at line: [59] in the jsp file: [/txnTrack.jsp]
The type com.fasterxml.jackson.core.exc.StreamReadException cannot be resolved. It is indirectly referenced from required .class files
56:             }
57: 
58:             ObjectMapper mapper = new ObjectMapper();
59:             Map data = mapper.readValue(apiResponse, Map.class);
60: 
61: %>
62:             <div class="box">


An error occurred at line: [59] in the jsp file: [/txnTrack.jsp]
The type com.fasterxml.jackson.core.type.TypeReference cannot be resolved. It is indirectly referenced from required .class files
56:             }
57: 
58:             ObjectMapper mapper = new ObjectMapper();
59:             Map data = mapper.readValue(apiResponse, Map.class);
60: 
61: %>
62:             <div class="box">


An error occurred at line: [59] in the jsp file: [/txnTrack.jsp]
The type com.fasterxml.jackson.core.type.ResolvedType cannot be resolved. It is indirectly referenced from required .class files
56:             }
57: 
58:             ObjectMapper mapper = new ObjectMapper();
59:             Map data = mapper.readValue(apiResponse, Map.class);
60: 
61: %>
62:             <div class="box">


An error occurred at line: [59] in the jsp file: [/txnTrack.jsp]
The type com.fasterxml.jackson.core.JsonProcessingException cannot be resolved. It is indirectly referenced from required .class files
56:             }
57: 
58:             ObjectMapper mapper = new ObjectMapper();
59:             Map data = mapper.readValue(apiResponse, Map.class);
60: 
61: %>
62:             <div class="box">


An error occurred at line: [59] in the jsp file: [/txnTrack.jsp]
The type com.fasterxml.jackson.core.ObjectCodec cannot be resolved. It is indirectly referenced from required .class files
56:             }
57: 
58:             ObjectMapper mapper = new ObjectMapper();
59:             Map data = mapper.readValue(apiResponse, Map.class);
60: 
61: %>
62:             <div class="box">


An error occurred at line: [59] in the jsp file: [/txnTrack.jsp]
The type com.fasterxml.jackson.core.Versioned cannot be resolved. It is indirectly referenced from required .class files
56:             }
57: 
58:             ObjectMapper mapper = new ObjectMapper();
59:             Map data = mapper.readValue(apiResponse, Map.class);
60: 
61: %>
62:             <div class="box">


An error occurred at line: [59] in the jsp file: [/txnTrack.jsp]
The method readValue(String, Class<Map>) from the type ObjectMapper refers to the missing type JsonProcessingException
56:             }
57: 
58:             ObjectMapper mapper = new ObjectMapper();
59:             Map data = mapper.readValue(apiResponse, Map.class);
60: 
61: %>
62:             <div class="box">


Stacktrace:
	org.apache.jasper.compiler.DefaultErrorHandler.javacError(DefaultErrorHandler.java:70)
