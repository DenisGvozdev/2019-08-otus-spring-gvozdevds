window.downloadFile = function (sUrl) {

    if (/(iP)/g.test(navigator.userAgent)) {
        alert('Your device does not support files downloading. Please try again in desktop browser.');
        return false;
    }

    if (window.downloadFile.isChrome || window.downloadFile.isSafari) {

        var link = document.createElement('a');
        link.href = sUrl;

        if (link.download !== undefined) {
            link.download = sUrl.substring(sUrl.lastIndexOf('/') + 1, sUrl.length);
        }

        if (document.createEvent) {
            var e = document.createEvent('MouseEvents');
            e.initEvent('click', true, true);
            link.dispatchEvent(e);
            return true;
        }
    }

    if (sUrl.indexOf('?') === -1) {
        sUrl += '?download';
    }

    window.open(sUrl, '_self');
    return true;
};

window.downloadFile.isChrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
window.downloadFile.isSafari = navigator.userAgent.toLowerCase().indexOf('safari') > -1;