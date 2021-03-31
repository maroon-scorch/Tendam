<#assign content>
        <div class="head">
            <h1 class="animate__animated animate__bounce center" id="main-title">Searching for Stars</h1>

            <form class="center" id="uploadForm">
                    <div class="ui right labeled left icon input large center" id="upload-space">
                        <i class="file icon"></i>
                        <input type="text" name="filepath" id="filepath" placeholder="e.g. ./data/stars/ten-star">
                    <div class="ui basic label">.csv</div>
                </div>
                <button onclick="return loadFile()" class="ui small button" id="button-update">Load File</button>
                <button onclick="return clearData()" class="ui small button center">Clear</button>
            </form>
        </div>
        <div class="ui container grid horizontal basic segments">
            <div class="six wide column ui segment" id="query-form">
                <form class="ui form"> <!--flex-outer-->
                    <div class="field" style="padding-top: 10px;">
                        <label for="query-type">QUERY TYPE</label>
                        <div class="field">
                            <select onchange="toggleQueryType()" name="query-type" id="query-type"
                                    class="ui fluid dropdown">
                                <option value="neighbors">Neighbors</option>
                                <option value="radius">Radius</option>
                            </select>
                        </div>
                    </div>
                    <div class="field">
                        <label for="approach">APPROACH</label>
                        <div class="field">
                            <select name="approach" id="approach" class="ui fluid dropdown">
                                <option value="naive">Naive (List)</option>
                                <option value="kdtree">KD Tree</option>
                            </select>
                        </div>
                    </div>
                    <div class="field">
                        <label for="search-by">SEARCH BY</label>
                        <div class="field">
                            <select onchange="toggleSearchMode()" name="search-by" id="search-by"
                                    class="ui fluid dropdown">
                                <option value="coordinate">Coordinate</option>
                                <option value="starname">Star Name</option>
                            </select>
                        </div>
                    </div>

                    <div class="two fields" id="input-coordinate">
                        <div class="field" id="input-x">
                            <label for="x">X-COORD.</label>
                            <input name="x" id="x" class="ui input" placeholder="X">
                        </div>
                        <div class="field" id="input-y">
                            <label for="y">Y-COORD.</label>
                            <input name="y" id="y" class="ui input" placeholder="X">
                        </div>
                        <div class="field" id="input-z">
                            <label for="z">Z-COORD.</label>
                            <input name="z" id="z" class="ui input" placeholder="X">
                        </div>
                    </div>

                    <div class="field inv" id="input-starname">
                        <label for="name">STAR NAME</label>
                        <input name="starname" id="starname" class="ui input" placeholder="Enter Star Name">
                    </div>
                    <div class="field" id="input-k">
                        <label for="k">NUMBER OF STARS</label>
                        <input name="k" id="k" class="ui input" placeholder="Enter Number">
                    </div>
                    <div class="field inv" id="input-r">
                        <label for="r">RADIUS</label>
                        <div class="field">
                            <input name="r" id="radius" class="ui input" placeholder="Enter Radius">
                        </div>
                    </div>
                    <button type="submit" class="ui button" id="button-query" onclick="checkValidation()">QUERY</button>
                    <div class="ui error message"></div>
                </form>
            </div>

            <div class="eight wide column ui segment results-scroll">
                <table class="ui single line table" id="output-stars">
                    <tbody>
                    <#list foundStars as star>
                        <tr>
                            <td class="collapsing">
                                <i class="certificate icon"></i> ${star.name}
                            </td>
                            <td>${star.getID()}</td>
                            <#assign coord = star.getCoordinate()>
                            <td class="right aligned collapsing">
                                (${coord[0]}, ${coord[1]}, ${coord[2]})
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="ui clear basic modal">
            <div class="ui icon header">
                <i class="trash icon"></i>
                Star Data Cleared
            </div>
            <div class="content center modal-content">
                <p>Upload data again to search for stars.</p>
            </div>
        </div>

        <div class="ui error basic modal">
            <div class="ui icon header">
                <i class="exclamation icon"></i>
                ERROR
            </div>
            <div class="content center modal-content">
                <p id="error-message"></p>
            </div>
        </div>

        <div class="ui success basic modal">
            <div class="ui icon header">
                <i class="check circle icon"></i>
                Success
            </div>
            <div class="content center modal-content">
                <p id="success-message"></p>
            </div>
        </div>
</#assign>
<#include "main.ftl">