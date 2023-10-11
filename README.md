# Anti-Fraud-System
A RESTful web service demonstrating (in a simplified form) the principles of anti-fraud systems in the financial sector. Project from hyperskill.org. Here's the link to the project:
<br />
<p align="center">
  <a href="https://hyperskill.org/projects/232">
    <img src="https://ictacademy.com.ng/wp-content/uploads/2020/02/1200px-JetBrains_Logo_2016.svg_.png" alt="Logo" height="80">
  </a>

<p align="center">Anti-Fraud System Project, is a graduate project for the Spring Security for Java Backend Developers track.
<p align="center"><img src="https://img.icons8.com/color/30/000000/java-coffee-cup-logo--v1.png" alt="Java">
  <img src="https://img.icons8.com/color/30/000000/spring-logo.png" alt="Spring Framework"><img src="https://www.h2database.com/html/images/h2-logo-2.png" width="30" height="30">
</p>

## Task Details
  <ol>
  <li><a href="Anti-Fraud System/Simple transaction validation/readme.md">Simple transaction validation</a>: Learn and practice the basics of Spring Web and REST controllers by implementing a simple logic that specifies the validity of a transaction with simple conditions</li>
  <li><a href="Anti-Fraud System/Authentication/readme.md">Authentication</a>: Implement user registration and authentication procedures using Spring Security</li>
  <li><a href="Anti-Fraud System/Authorization/readme.md">Authorization</a>: Implement roles and grant access rights to users</li>
  <li><a href="Anti-Fraud System/Stolen cards   suspicious IPs/readme.md">Stolen cards & suspicious IPs</a>: Upgrade the fraud detection rules by taking advantage of stolen card data and suspicious IP-address pool.</li>
  <li><a href="Anti-Fraud System/Rule-based system/readme.md">Rule-based system</a>: Create a rule-based system, a simple and effective artificial intelligence method, and practice complex conditional logic.</li>
  <li><a href="Anti-Fraud System/Feedback/readme.md">Feedback</a>: Add feedback to the validation process.</li>
  </ol>

## Usage Examples
<p><strong>Example 1: </strong><em>a POST request for /api/antifraud/transaction</em></p>
<p><em>Request body:</em></p>
<pre><code class="language-json">{
  "amount": 210,
  "ip": "192.168.1.1",
  "number": "4000008449433403",
  "region": "EAP",
  "date": "2022-01-22T16:04:00"
}</code></pre>
<p><em>Response: </em><code class="language-json">200 OK</code></p>
<p><em>Response body:</em></p>
<pre><code class="language-json">{
   "result": "MANUAL_PROCESSING",
   "info": "amount"
}</code></pre>
<p><strong>Example 2: </strong><em>a GET request for /api/antifraud/history/4000008449433403</em></p>
<p><em>Response: </em><code class="language-json">200 OK</code></p>
<p><em>Response body:</em></p>
<pre><code class="language-json">[
  {
  "transactionId": 1,
  "amount": 210,
  "ip": "192.168.1.1",
  "number": "4000008449433403",
  "region": "EAP",
  "date": "2022-01-22T16:04:00",
  "result": "MANUAL_PROCESSING",
  "feedback": ""
  }
]</code></pre>
<p><strong>Example 3: </strong><em>a PUT request for </em>/api/antifraud/transaction</p>
<p><em>Request body:</em></p>
<pre><code class="language-json">{
   "transactionId": 1,
   "feedback": "ALLOWED"
}</code></pre>
<p><em>Response: </em><code class="language-json">200 OK</code></p>
<p><em>Response body:</em></p>
<pre><code class="language-json">{
  "transactionId": 1,
  "amount": 210,
  "ip": "192.168.1.1",
  "number": "4000008449433403",
  "region": "EAP",
  "date": "2022-01-22T16:04:00",
  "result": "MANUAL_PROCESSING",
  "feedback": "ALLOWED"
}</code></pre>
