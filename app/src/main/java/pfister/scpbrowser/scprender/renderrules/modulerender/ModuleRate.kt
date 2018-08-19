package pfister.scpbrowser.scprender.renderrules.modulerender

class ModuleRate : ModuleRender {
    companion object {
        val RATE_CSS:String = """
            .page-rate-widget-box{

}

.page-rate-widget-box .rate-points{
	background-color: #666;
	color: #FFF;
	font-weight: bold;
	padding: 0 10px;
	-moz-border-radius: 4px 0 0 4px;
}

.page-rate-widget-box a{
	text-decoration: none;
	color: #000;
	background-color: #DDD;
	padding: 0 5px;
}
.page-rate-widget-box a:hover{
	background-color: #EEE;
}

.page-rate-widget-box .cancel, .page-rate-widget-box .cancel a{
	-moz-border-radius:  0 4px 4px  0;
}
        """.trimIndent()
        val RATE_HTML:String =
                """
                    <div style="text-align: right">
    <div class="page-rate-widget-box">
        <span class="rate-points">
            rating:&nbsp;
            <span class="number prw54353">
                +%rating
            </span>
        </span>
        <span class="rateup btn btn-default">
            <a title="I like it" href="javascript:;" onclick="WIKIDOT.modules.PageRateWidgetModule.listeners.rate(event, 1)">
                +
            </a>
        </span>
        <span class="ratedown btn btn-default">
            <a title="I don't like it" href="javascript:;" onclick="WIKIDOT.modules.PageRateWidgetModule.listeners.rate(event, -1)">
                –
            </a>
        </span>
        <span class="cancel btn btn-default">
            <a title="Cancel my vote" href="javascript:;" onclick="WIKIDOT.modules.PageRateWidgetModule.listeners.cancelVote(event)">
                x
            </a>
        </span>
    </div>
</div>
                """.trimIndent()
    }
    override val module_name: String = "rate"

    override fun render(attr: Map<String, String>, body: String): String {
        val rating = "todo" // Todo: Implement rating
        return "<style>\n" + RATE_CSS + "\n</style>\n" + RATE_HTML.replace("%rating",rating)
    }
}