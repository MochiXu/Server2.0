/**
 * Created by 30947 on 2018/7/20.
 */
$(function () {
    getHt();
    initMap();
    initMap01();
    mapActive();
    page();
    mapRestList();
    rightChange();
    char1();
    char2();
    $(".map_work>ul").hide();
    $(".map_resList").show();

});

var map;
var markers = [];
var source_id_clicked = 'source01';
var layer01;
var layer02;
var layer03;
var layer04;
var layer05;
var layer06;
var layer07;

var trash_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAACXBIWXMAAAsTAAALEwEAmpwYAAAKxGlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiB4bXA6Q3JlYXRlRGF0ZT0iMjAyMC0wMi0xM1QxNzozNzo0MCswODowMCIgeG1wOk1vZGlmeURhdGU9IjIwMjAtMDItMTNUMTc6NDU6MDErMDg6MDAiIHhtcDpNZXRhZGF0YURhdGU9IjIwMjAtMDItMTNUMTc6NDU6MDErMDg6MDAiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NDU0MGUyZmQtMWFjNi0zZjQyLWJjODctNTc5ZDBlNTMxOTcyIiB4bXBNTTpEb2N1bWVudElEPSJhZG9iZTpkb2NpZDpwaG90b3Nob3A6MDZiY2UwMzYtMGQxYi0xNjQ0LWFhYzgtODhjNzA2NzgxZjZmIiB4bXBNTTpPcmlnaW5hbERvY3VtZW50SUQ9InhtcC5kaWQ6YjgzZjQxMTQtM2ZiOS0wMjQ5LWFjNGItYjYwOWMwYTAxYWM0Ij4gPHhtcE1NOkhpc3Rvcnk+IDxyZGY6U2VxPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY3JlYXRlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDpiODNmNDExNC0zZmI5LTAyNDktYWM0Yi1iNjA5YzBhMDFhYzQiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMTc6Mzc6NDArMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY29udmVydGVkIiBzdEV2dDpwYXJhbWV0ZXJzPSJmcm9tIGltYWdlL3BuZyB0byBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDoyMGRkOGFkMi1kNTE1LTlmNGQtODI4MC02YTU0OGM5OGRjOTAiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMTc6NDI6MzQrMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6ZjExZjBjMjgtMmYzMC02MzQ1LThhMGItMDM2ZjBiZDE5ZWRiIiBzdEV2dDp3aGVuPSIyMDIwLTAyLTEzVDE3OjQyOjU4KzA4OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNvbnZlcnRlZCIgc3RFdnQ6cGFyYW1ldGVycz0iZnJvbSBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIHRvIGltYWdlL3BuZyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iZGVyaXZlZCIgc3RFdnQ6cGFyYW1ldGVycz0iY29udmVydGVkIGZyb20gYXBwbGljYXRpb24vdm5kLmFkb2JlLnBob3Rvc2hvcCB0byBpbWFnZS9wbmciLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjFmMmZhMmUzLTA0Y2EtNmQ0Mi05ZWIwLTE5MGRjMTcwY2NkYyIgc3RFdnQ6d2hlbj0iMjAyMC0wMi0xM1QxNzo0Mjo1OCswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo0NTQwZTJmZC0xYWM2LTNmNDItYmM4Ny01NzlkMGU1MzE5NzIiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMTc6NDU6MDErMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6ZjExZjBjMjgtMmYzMC02MzQ1LThhMGItMDM2ZjBiZDE5ZWRiIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOmI4M2Y0MTE0LTNmYjktMDI0OS1hYzRiLWI2MDljMGEwMWFjNCIgc3RSZWY6b3JpZ2luYWxEb2N1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';
var service_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAACXBIWXMAAAsTAAALEwEAmpwYAAAKxGlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiB4bXA6Q3JlYXRlRGF0ZT0iMjAyMC0wMi0xM1QyMDoyMDozMSswODowMCIgeG1wOk1vZGlmeURhdGU9IjIwMjAtMDItMTNUMjA6NDc6MDYrMDg6MDAiIHhtcDpNZXRhZGF0YURhdGU9IjIwMjAtMDItMTNUMjA6NDc6MDYrMDg6MDAiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NDY2N2JiOTUtYmM2MS0wMDQxLTliN2QtZGI5NTNjYTViNTM5IiB4bXBNTTpEb2N1bWVudElEPSJhZG9iZTpkb2NpZDpwaG90b3Nob3A6MTViODY2YmEtN2M1NS00ZDQ5LTgyODQtMjA4MzMyZWE2ZGQ1IiB4bXBNTTpPcmlnaW5hbERvY3VtZW50SUQ9InhtcC5kaWQ6MmEzZWM2N2EtOTQxYi05ZTQ2LTkxYjctOTQ5M2U0OWIzOGZjIj4gPHhtcE1NOkhpc3Rvcnk+IDxyZGY6U2VxPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY3JlYXRlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDoyYTNlYzY3YS05NDFiLTllNDYtOTFiNy05NDkzZTQ5YjM4ZmMiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMjA6MjA6MzErMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY29udmVydGVkIiBzdEV2dDpwYXJhbWV0ZXJzPSJmcm9tIGltYWdlL3BuZyB0byBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDozY2JhNWJmOS02MmM2LTE3NGUtODgwNi1kNTFmZDQzYzgwNDQiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMjA6MzQ6MjArMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6ZDEzZGUwZGQtNzgwYS03OTRkLTlhMzUtMDM5NGQwM2UzODU4IiBzdEV2dDp3aGVuPSIyMDIwLTAyLTEzVDIwOjM0OjM5KzA4OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNvbnZlcnRlZCIgc3RFdnQ6cGFyYW1ldGVycz0iZnJvbSBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIHRvIGltYWdlL3BuZyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iZGVyaXZlZCIgc3RFdnQ6cGFyYW1ldGVycz0iY29udmVydGVkIGZyb20gYXBwbGljYXRpb24vdm5kLmFkb2JlLnBob3Rvc2hvcCB0byBpbWFnZS9wbmciLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjY0YTFlMWZiLWFhNGMtOTE0ZS04MDAzLWZjN2ZhODhmYzdhYiIgc3RFdnQ6d2hlbj0iMjAyMC0wMi0xM1QyMDozNDozOSswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo0NjY3YmI5NS1iYzYxLTAwNDEtOWI3ZC1kYjk1M2NhNWI1MzkiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMjA6NDc6MDYrMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6ZDEzZGUwZGQtNzgwYS03OTRkLTlhMzUtMDM5NGQwM2UzODU4IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjJhM2VjNjdhLTk0MWItOWU0Ni05MWI3LTk0OTNlNDliMzhmYyIgc3RSZWY6b3JpZ2luYWxEb2N1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';
var park_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAACXBIWXMAAAsTAAALEwEAmpwYAAAFHGlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIwLTAyLTEzVDIwOjQ0OjUxKzA4OjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMC0wMi0xM1QyMjowNToxMiswODowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAyMC0wMi0xM1QyMjowNToxMiswODowMCIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIHBob3Rvc2hvcDpJQ0NQcm9maWxlPSJzUkdCIElFQzYxOTY2LTIuMSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo3Mzc5MzEwOS01Nzkx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';
var quickapp_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAACXBIWXMAABJ0AAASdAHeZh94AAAJ72lUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0RXZ0PSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VFdmVudCMiIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiB4bXA6Q3JlYXRlRGF0ZT0iMjAyMC0wMi0xM1QxODowODozMSswODowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAyMC0wMi0xM1QxODoxMToxMyswODowMCIgeG1wOk1vZGlmeURhdGU9IjIwMjAtMDItMTNUMTg6MTE6MTMrMDg6MDAiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjJlNDhjZmRlLTI5Y2MtOTA0NS04NjE3LTVlMjZlMWZiOTM4MyIgeG1wTU06RG9jdW1lbnRJRD0iYWRvYmU6ZG9jaWQ6cGhvdG9zaG9wOjRjMGM5NmI1LTBiNGEtZmI0MS1iMDQ1LTNlYWZhOWY3NDM4YyIgeG1wTU06T3JpZ2luYWxEb2N1bWVudElEPSJ4bXAuZGlkOmVmOTM4Mjc3LWZiYzItYzg0MC1iZGNmLWJlOThiYThjMzEzYyIgcGhvdG9zaG9wOkNvbG9yTW9kZT0iMyI+IDx4bXBNTTpIaXN0b3J5PiA8cmRmOlNlcT4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNyZWF0ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6ZWY5MzgyNzctZmJjMi1jODQwLWJkY2YtYmU5OGJhOGMzMTNjIiBzdEV2dDp3aGVuPSIyMDIwLTAyLTEzVDE4OjA4OjMxKzA4OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjViNzM1ZTA1LTk3ZDktNDk0Yi04NDBjLWJiY2U1MDdmMWUwYiIgc3RFdnQ6d2hlbj0iMjAyMC0wMi0xM1QxODoxMTowMiswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo4MjFiMGZkYS04MzQzLWYyNDYtODk2Yy1mMWVmNDFkMGEzMTciIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMTg6MTE6MTMrMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY29udmVydGVkIiBzdEV2dDpwYXJhbWV0ZXJzPSJmcm9tIGFwcGxpY2F0aW9uL3ZuZC5hZG9iZS5waG90b3Nob3AgdG8gaW1hZ2UvcG5nIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJkZXJpdmVkIiBzdEV2dDpwYXJhbWV0ZXJzPSJjb252ZXJ0ZWQgZnJvbSBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIHRvIGltYWdlL3BuZyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6MmU0OGNmZGUtMjljYy05MDQ1LTg2MTctNWUyNmUxZmI5MzgzIiBzdEV2dDp3aGVuPSIyMDIwLTAyLTEzVDE4OjExOjEzKzA4OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPC9yZGY6U2VxPiA8L3htcE1NOkhpc3Rvcnk+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjgyMWIwZmRhLTgzNDMtZjI0Ni04OTZjLWYxZWY0MWQwYTMxNyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDplZjkzODI3Ny1mYmMyLWM4NDAtYmRjZi1iZTk4YmE4YzMxM2MiIHN0UmVmOm9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDplZjkzODI3Ny1mYmMyLWM4NDAtYmRjZi1iZTk4YmE4YzMxM2MiLz4gPHBob3Rvc2hvcDpEb2N1bWVudEFuY2VzdG9ycz4gPHJkZjpCYWc+IDxyZGY6bGk+eG1wLmRpZDpGREM1Nzk2NjIzODUxMUU4QjVBREMxNTJDODg3RTk5MzwvcmRmOmxpPiA8L3JkZjpCYWc+IDwvcGhvdG9zaG9wOkRvY3VtZW50QW5jZXN0b3JzPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Ph7kUtEAAAdDSURBVFiFzZh/bJVXGcc/533f+6ul9AdtKRVoGXW0UAqyrhSmsGU/MJuMuEydY3NxwmLYcAn4I/EPExP9w20ihk1jwBlxiIuJJso2J2RmSwSGdMA6yg8L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';
var rest_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAEL0lEQVRYR82YTWhcVRSAvzPJiz/YNokIVvGnQkvcdOGiGwsVq2JRa/1F1NKFpeAuGd1160KoNEURVzW1YhMRTAuBVlGxoFRQ0CwkiqAYsNU2TWwSM39v3pVz30s68zpv7h2dIT0wzAzv3HO/e37uffcINTI4bnqlxB4D9wG9En/7SQREIPppNsKAMZd10f85QoRFAiYO7pbdtcNXbA1+aHZhGBa4048I1DhVDyg1GIFJdLPsRwYkR7lwHY+884J8pnoWUOHEMO4LJgoWgv12iIWqJovJ0LVmQojUpv7OQbWPHW8+L6dEw0qJ7708pyt0eGGFQUNXcS8iUviwwQK6CYdflkAGx8ygwLDLE9YLCudS1Ll0Qg/dqByHPktkDRMyNGaOA483m1fU9U0M1Xmt7F6EFolROEeKSEBRhkbNlwjbGgFab3mEyY7VyXRSlxiwnvPJXy2SoTFb9A1FWoBTjzjD3wLc8pozAb3hdPvQhbTRc7WmGgK2BKeec9D55lzDNEuHuFFB9HTD9s1wa3/KhE/haLolelPTMHsJfpmGila6h9R5UDde9V5a9j8Ffdd7WHOonC/EClO/wYkv/OzVA2Yk+ht1p6Of4UZay4DlEA6+52fnMmAIuYyQtRtQ0V4/3ApgEtqsXO8E4Nw8rFsD84vw+1k4/R0sFa+Eth50VW0nANMoCnz4Ywj1SK2RGNCxyXYC8KNPYfoc3LYetm+BG3tjL56ZTAHmjxmjW0sz6QRgbQ4ObIBd98NfMzByIg141BjXKdBpwP51sO9p+KcAbx1LAb5yNPssXlbtNOCmO+DJB2BhCd4evcoA774LdmyFngC+/RE+/+YqArypH156IgbSrUYL54oqXs0QBwE8fC9c/BvO/ND4FVFWA7AawYERz5PEB/C15+DawM9gllYYwWwpfvrnDBxJbSdZ49weNJB/DG7p+3+AhRAWkjelyZ/g5Nd+9tyAEQysh70P+RnM0rpYhKoBDe+R43Bhzs+eGzC5tz67DbZs9DOa1looQ6EaF8Gpr2DyZ387TkC949o7MXDPRnhmKwRdfhOox+bLUNH8uwQTp+HsBb+xy1pOQL2A25t/Ijlg0+2weQOsTb1l2zPJQKEEswswvxSfDn+chxnPkKbxWwZstn7tw+gNr53iBtS3bM9J9XJkOwZtFDegb8cgub21FVBA8u+bOTH0Nl10ssE6HaOdA19dpzEwXRjJf2CmpMpAWwC1qVVyt9w82GKVbhYlP2oOSIVXmyZ/zVbjMh5pvqbuFa4xmc/XMmIbmLklfpUq2YfZahTKNVSG90nPSgs4V2E8qweodSIt5JbNQ4/2WqbnBIp9PKh96romei7k3UxPaug8ezH/NczWEQGhuYGdh16Uk7qAuvuSDXeR/UQ8SsTNddWdOlHatWFbR+cw0kXFdPPJob2ys9b2v9A9Cey6DhBkAAAAAElFTkSuQmCC';
var toilet_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAACXBIWXMAAAsTAAALEwEAmpwYAAAF7GlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIwLTAyLTEzVDIyOjI2OjU1KzA4OjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMC0wMi0xM1QyMjoyODoyNyswODowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAyMC0wMi0xM1QyMjoyODoyNyswODowMCIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIHBob3Rvc2hvcDpJQ0NQcm9maWxlPSJzUkdCIElFQzYxOTY2LTIuMSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozNzNjMjk1Mi0yNDFi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';
//获取div的高度
function getHt() {
    var all_height = $(window).height();
    var div_height = all_height - 84;
    $("#car_control").css("height", div_height + "px");


}

//加载地图
//地图界面高度设置
function initLegend(colorLegend) {
    var legends = colorLegend.map(function (item) {
        return `<li class="color-item" style="background-color: ${item.color}"></li>`
    });

    var ranges = colorLegend.map((item, index) => {
        // range 可能为小数，可以自行取整计算
        item.range[0] = Math.round(item.range[0]);
        item.range[1] = Math.round(item.range[1]);

        if (index == colorLegend.length - 1) {
            return `<li class="label-item">${item.range[0]}</li><li class="label-item">${item.range[1]}</li>`;
        }
        return `<li class="label-item">${item.range[0]}</li>`;
    });

    document.getElementById('legend-color').innerHTML = legends.join('');
    document.getElementById('legend-label').innerHTML = ranges.join('');
}

function initMap() {
    map = new AMap.Map('map_box', {
        pitch: 66,
        center: [122.108747, 37.505073],
        zoom: 15,
        mapStyle: 'amap://styles/grey',
        viewMode: '3D'
    });
}


//人流空间分布
function initMap01() {
    layer01 = new Loca.HexagonLayer({
        map: map,
        fitView: false,
        eventSupport: true
    });
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=1', true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            //声明一个json数组对象，用于地图的数据源
            var jsonData = eval(request.responseText);

            layer01.on('legendupdate', function (ev) {
                var colorLegend = ev.colorLegend;
                console.log(colorLegend);
                initLegend(colorLegend);
            });

            layer01.setData(jsonData, {
                lnglat: 'lnglat',
                value: 'cnt'
            });

            layer01.setOptions({
                unit: 'meter',
                heightUnit: 'meter',
                style: {
                    color: [
                        '#0553A1',
                        '#0B79B0',
                        '#10B3B0',
                        '#7CCF98',
                        '#DCE872'
                    ],
                    height: [0, 500],
                    radius: 15,
                    gap: 2,
                    opacity: 1,
                },
                selectStyle: {
                    color: '#0A0F1E',
                    opacity: 1
                }
            });
            layer01.render();
        }
    }
}

//活跃的快应用
function initMap02() {

    layer02 = new Loca.IconLayer({
        map: map,
        fitView: false,
        eventSupport: true
    });
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=2', true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            //声明一个json数组对象，用于地图的数据源
            var jsonData = eval(request.responseText);
            layer02.setData(jsonData, {
                lnglat: 'lnglat'
            });

            layer02.setOptions({
                source: quickapp_png,
                style: {
                    size: 18
                }
            });
            layer02.render();

        }
    }
}

//公共服务
function initMap03() {
    layer03 = new Loca.IconLayer({
        map: map,
        fitView: false
    });
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=3', true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            //声明一个json数组对象，用于地图的数据源
            var jsonData = eval(request.responseText);
            layer03.setData(jsonData, {
                lnglat: 'lnglat'
            });
            layer03.setOptions({
                source: service_png,
                style: {
                    size: 18
                }
            });
            layer03.render();
            //显示服务中心的名字
            for (var i = 0; i < jsonData.length; i++) {
                var name = jsonData[i].service_center_name;
                marker = new AMap.Marker({ //添加自定义点标记
                    map: map,
                    position: [jsonData[i].service_center_lng, jsonData[i].service_center_lat], //基点位置
                    draggable: false, //是否可拖动
                    content: '<div style="color: #25f3e6;border:1px;solid #ccc;background:#515250;white-space:nowrap; padding:3px;font-size:14px;">' + name + '</div>' //自定义点标记覆盖物内容
                });
                markers.push(marker);
            }
        }
    }

}

//停车场
function initMap04() {
    layer04 = new Loca.IconLayer({
        map: map,
        fitView: false
    });
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=4', true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            //声明一个json数组对象，用于地图的数据源
            var jsonData = eval(request.responseText);
            layer04.setData(jsonData, {
                lnglat: 'lnglat'
            });
            layer04.setOptions({
                source:park_png,
                style: {
                    size: 18
                }
            });
            layer04.render();
            //显示停车场的名字
            for (var i = 0; i < jsonData.length; i++) {
                var name = jsonData[i].park_name;
                marker = new AMap.Marker({ //添加自定义点标记
                    map: map,
                    position: [jsonData[i].park_lng, jsonData[i].park_lat], //基点位置
                    draggable: false, //是否可拖动
                    content: '<div style="color: #25f3e6;border:1px;solid #ccc;background:#515250;white-space:nowrap; padding:3px;font-size:14px;">' + name + '</div>' //自定义点标记覆盖物内容
                });
                markers.push(marker);
            }
        }
    }

}

//垃圾桶
function initMap05() {
    layer05 = new Loca.IconLayer({
        map: map,
        fitView: false
    });
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=5', true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            //声明一个json数组对象，用于地图的数据源
            var jsonData = eval(request.responseText);
            layer05.setData(jsonData, {
                lnglat: 'lnglat'
            });
            layer05.setOptions({
                source: trash_png,
                style: {
                    size: 18
                }
            });
            layer05.render();
            // //显示垃圾桶的名字
            // for (var i=0;i<jsonData.length;i++){
            //     var name=jsonData[i].trash_name;
            //     marker = new AMap.Marker({ //添加自定义点标记
            //         map: map,
            //         position: [jsonData[i].trash_lng, jsonData[i].trash_lat], //基点位置
            //         draggable: false, //是否可拖动
            //         content: '<div style="color: #25f3e6 border:1px solid #ccc;background:#515250;white-space:nowrap; padding:3px;font-size:14px;">'+name+'</div>' //自定义点标记覆盖物内容
            //     });
            //     markers.push(marker);
            // }
        }
    }

}

//厕所
function initMap06() {
    layer06 = new Loca.IconLayer({
        map: map,
        fitView: false
    });
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=6', true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            //声明一个json数组对象，用于地图的数据源
            var jsonData = eval(request.responseText);
            layer06.setData(jsonData, {
                lnglat: 'lnglat'
            });
            layer06.setOptions({
                source: toilet_png,
                style: {
                    size: 18
                }
            });
            layer06.render();
            //显示厕所的名字
            for (var i = 0; i < jsonData.length; i++) {
                var name = jsonData[i].toilet_name;
                marker = new AMap.Marker({ //添加自定义点标记
                    map: map,
                    position: [jsonData[i].toilet_lng, jsonData[i].toilet_lat], //基点位置
                    draggable: false, //是否可拖动
                    content: '<div style="color: #25f3e6; border:1px; solid #ccc;background:#515250;white-space:nowrap; padding:3px;font-size:14px;">' + name + '</div>' //自定义点标记覆盖物内容
                });
                markers.push(marker);
            }
        }
    }

}

//休息点
function initMap07() {
    layer07 = new Loca.IconLayer({
        map: map,
        fitView: false
    });
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=7', true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            //声明一个json数组对象，用于地图的数据源
            var jsonData = eval(request.responseText);
            layer07.setData(jsonData, {
                lnglat: 'lnglat'
            });
            layer07.setOptions({
                source:rest_png,
                style: {
                    size: 18
                }
            });
            layer07.render();
            //显示歇息处的名字
            for (var i = 0; i < jsonData.length; i++) {
                var name = jsonData[i].rest_name;
                marker = new AMap.Marker({ //添加自定义点标记
                    map: map,
                    position: [jsonData[i].rest_lng, jsonData[i].rest_lat], //基点位置
                    draggable: false, //是否可拖动
                    content: '<div style="color: #25f3e6; border:1px; solid #ccc;background:#515250;white-space:nowrap; padding:3px;font-size:14px;">' + name + '</div>' //自定义点标记覆盖物内容
                });
                markers.push(marker);
            }
        }
    }

}

//工具条点击效果
function mapActive() {
    $(".map_top>ul>li").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
        $(this).find("a").addClass("active");
        $(this).find("a").parents("li").siblings().find("a").removeClass("active");
    })
}

//统计分析图1
function char1() {
    var myChart = echarts.init($("#char1")[0]);

    //设置数组，记录景区景点名称【更新频率：每十分钟】
    var data_legend = new Array();
    data_legend[0] = '景点A'
    data_legend[1] = '景点B'
    data_legend[2] = '景点C'
    data_legend[3] = '景点D'
    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_index_charts?chart=1', true);
        request.send();
        //3.监听
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                //声明一个json数组对象，用于图表data
                var jsonData = [];
                //获取从后端接受的json字符串
                var XHR = request.responseText;
                //解析json字符串，使其成为json对象
                var objs = eval(XHR);

                //对图表内的两组数据进行赋值
                for (var j = 0; j < objs.length; j++) {
                    var json = {};//临时json变量
                    data_legend[j] = objs[j].scenic_name;
                    json.value = objs[j].person_count;
                    json.name = objs[j].scenic_name;
                    jsonData.push(json)
                }
                option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'right',
                        textStyle: {
                            color: '#ffffff',

                        },
                        data: data_legend
                    },

                    calculable: false,
                    series: [
                        {
                            name: '景点名称',
                            type: 'pie',
                            radius: ['40%', '70%'],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    },
                                    labelLine: {
                                        show: false
                                    }
                                },
                                emphasis: {
                                    label: {
                                        show: true,
                                        position: 'center',
                                        textStyle: {
                                            fontSize: '20',
                                            fontWeight: 'bold'
                                        }
                                    }
                                }
                            },
                            data: jsonData
                        }
                    ]
                };

                myChart.setOption(option);
                window.addEventListener('resize', function () {
                    myChart.resize();
                })
            }
        }
    }, 3000);
}
//统计分析图2
function char2() {

    var myChart = echarts.init($("#char2")[0]);

    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_index_charts?chart=2', true);
        request.send();
        //3.监听
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                //声明一个json数组对象，用于图表data
                //声明四个数组对象，存储随机获取到的四个景点游客游玩时长数据
                var arrScenicName=new Array();
                var arrScenic01 = new Array();
                var arrScenic02 = new Array();
                var arrScenic03 = new Array();
                var arrScenic04 = new Array();
                //获取从后端接受的json字符串
                var XHR = request.responseText;
                //alert(XHR);
                //解析json字符串，使其成为json对象
                var objs = eval(XHR);
                //对图表内的两组数据进行赋值
                for (var j = 0; j < objs.length; j++) {
                    arrScenicName.push(objs[j].scenic_name);
                    arrScenic01.push(objs[j].h01);
                    arrScenic02.push(objs[j].h02);
                    arrScenic03.push(objs[j].h03);
                    arrScenic04.push(objs[j].h04);
                }
                option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {show: 'true', borderWidth: '0'},
                    legend: {
                        data: ['<1h', '1h~2h', '2h~3h', '>3h'],
                        textStyle: {
                            color: '#ffffff'
                        }
                    },

                    calculable: false,
                    xAxis: [
                        {
                            type: 'value',
                            axisLabel: {
                                show: true,
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            splitLine: {
                                lineStyle: {
                                    color: ['#f2f2f2'],
                                    width: 0,
                                    type: 'solid'
                                }
                            }

                        }
                    ],
                    yAxis: [
                        {
                            type: 'category',
                            data: arrScenicName,
                            axisLabel: {
                                show: true,
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            splitLine: {
                                lineStyle: {
                                    width: 0,
                                    type: 'solid'
                                }
                            }
                        }
                    ],
                    series: [
                        {
                            name: '<1h',
                            type: 'bar',
                            stack: '总量',
                            itemStyle: {normal: {label: {show: true, position: 'insideRight'}}},
                            data: arrScenic01
                        },
                        {
                            name: '1h~2h',
                            type: 'bar',
                            stack: '总量',
                            itemStyle: {normal: {label: {show: true, position: 'insideRight'}}},
                            data: arrScenic02
                        },
                        {
                            name: '2h~3h',
                            type: 'bar',
                            stack: '总量',
                            itemStyle: {normal: {label: {show: true, position: 'insideRight'}}},
                            data: arrScenic03
                        },
                        {
                            name: '>3h',
                            type: 'bar',
                            stack: '总量',
                            itemStyle: {normal: {label: {show: true, position: 'insideRight'}}},
                            data: arrScenic04
                        }

                    ]
                };
                myChart.setOption(option);
                window.addEventListener('resize', function () {myChart.resize();})
            }
        }
    }, 3000);


}


function page() {
    $("#page").Page({
        totalPages: 1,//分页总数
        liNums: 1,//分页的数字按钮数(建议取奇数)
        activeClass: 'activP', //active 类样式定义
        callBack: function (page) {
            //console.log(page)
        }
    });
}

//专题图点击
function mapRestList() {
    $(".map_work>ul>li").click(function () {
        $(".map_work>ul").hide();
        $(".map_resList").show();
    })
}

//返回
function back() {
    // $(".map_work>ul").show();
    // $(".map_resList").hide();
}

//右侧功能界面切换
function rightChange() {
    $(".map_right_top>ul>li").click(function () {
        var ins = $(this).index();
        $(this).addClass("li_active").siblings().removeClass("li_active");
        $(".map_con .map_con_div").eq(ins).show().siblings().hide();
    })
}

//check点击事件，切换地图源
function checkbox_Click(object) {
    var items = $("#map_source").children("p");
    //取消其它所有元素被选中状态
    for (var i = 0; i < items.length; i++) {
        $(items[i]).children("input").prop("checked", false);
    }
    object.checked = true;
    source_id_clicked = object.id;
    //清空所有的layer与marker
    clearMarker();
    if (layer01)
        layer01.setMap(null);
    if (layer02)
        layer02.setMap(null);
    if (layer03)
        layer03.setMap(null);
    if (layer04)
        layer04.setMap(null);
    if (layer05)
        layer05.setMap(null);
    if (layer06)
        layer06.setMap(null);
    if (layer07)
        layer07.setMap(null);
    switch (object.id) {
        case "source01":
            initMap01();
            break;
        case "source02":
            initMap02();
            break;
        case "source03":
            initMap03();
            break;
        case "source04":
            initMap04();
            break;
        case "source05":
            initMap05();
            break;
        case "source06":
            initMap06();
            break;
        case "source07":
            initMap07();
            break;
    }
}
//check2点击事件，切换地图风格
function checkbox_Click2(object) {
    var items = $("#style_change").children("p");
    //取消其它所有元素被选中状态
    for (var i = 0; i < items.length; i++) {
        $(items[i]).children("input").prop("checked", false);
    }
    object.checked = true;
    console.log("clicked "+object.id);
    switch (object.id) {
        case "style01":
            map.setMapStyle('amap://styles/normal');
            break;
        case "style02":
            map.setMapStyle('amap://styles/macaron');
            break;
        case "style03":
            map.setMapStyle('amap://styles/graffiti');
            break;
        case "style04":
            map.setMapStyle('amap://styles/dark');
            break;
        case "style05":
            map.setMapStyle('amap://styles/darkblue');
            break;
        case "style06":
            map.setMapStyle('amap://styles/grey');
            break;

    }

}


setInterval(function () {
    //清空所有的layer与marker
    clearMarker();
    if (layer01)
        layer01.setMap(null);
    if (layer02)
        layer02.setMap(null);
    if (layer03)
        layer03.setMap(null);
    if (layer04)
        layer04.setMap(null);
    if (layer05)
        layer05.setMap(null);
    if (layer06)
        layer06.setMap(null);
    if (layer07)
        layer07.setMap(null);
    switch (source_id_clicked) {
        case "source01":
            initMap01();
            break;
        case "source02":
            initMap02();
            break;
        case "source03":
            initMap03();
            break;
        case "source04":
            initMap04();
            break;
        case "source05":
            initMap05();
            break;
        case "source06":
            initMap06();
            break;
        case "source07":
            initMap07();
            break;
    }
}, 8000);

// 清除 marker
function clearMarker() {
    map.remove(markers);
}

//更新车辆的数据
setInterval(function () {
//1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=4', true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var jsonData=eval(request.responseText);
            for (var i = 0; i < jsonData.length; i++) {
                //更新车位剩余量
                var surplus=jsonData[i].park_total-jsonData[i].park_occupy;
                $("#"+jsonData[i].park_id+"_Surplus").text("剩余:"+surplus+"辆");
                //更新日期
                $("#"+jsonData[i].park_id+"_Update").text("更新日期:"+jsonData[i].park_date+" "+jsonData[i].park_time);
                //更新车位总量
                $("#"+jsonData[i].park_id+"_Total").text("总数:"+jsonData[i].park_total+"辆");
            }

        }
    }
},10000);