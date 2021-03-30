<!DOCTYPE html lang="en">
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
  <link rel="stylesheet" href="css/normalize.css">
  <link rel="stylesheet" href="css/html5bp.css">
  <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/query.css">
    <link rel="stylesheet" href="css/results.css">

    <!-- Fomantic -->
    <link href="https://cdn.bootcdn.net/ajax/libs/semantic-ui/2.4.1/semantic.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>

    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"
    />

    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans&family=Work+Sans:wght@700&display=swap" rel="stylesheet">

  </head>
  <body style="background-color: #fce5cd">
     ${content}
     <!-- Again, we're serving up the unminified source for clarity. -->
        <script src="js/jquery-2.1.1.js"></script>
        <script src="js/query.js"></script>
     </body>
     <!-- See http://html5boilerplate.com/ for a good place to start
          dealing with real world issues like old browsers.  -->
</html>