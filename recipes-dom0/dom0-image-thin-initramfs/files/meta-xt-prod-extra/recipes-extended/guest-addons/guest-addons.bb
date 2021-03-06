SUMMARY = "config files and scripts for a guest"
DESCRIPTION = "config files and scripts for guest which will be running for tests"

require inc/xt_shared_env.inc

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "\
    file://domd-salvator-x-m3.cfg \
    file://domd-salvator-x-h3.cfg \
    file://doma-salvator-x-m3.cfg \
    file://doma-salvator-x-h3.cfg \
    file://domf.cfg \
    file://guest_doma \
    file://guest_domd \
    file://guest_domf \
    file://start_guest.sh \
"

S = "${WORKDIR}"

DOMD_CONFIG_salvator-x-m3-xt = "domd-salvator-x-m3.cfg"
DOMD_CONFIG_salvator-x-h3-xt = "domd-salvator-x-h3.cfg"
DOMA_CONFIG_salvator-x-m3-xt = "doma-salvator-x-m3.cfg"
DOMA_CONFIG_salvator-x-h3-xt = "doma-salvator-x-h3.cfg"

FILES_${PN} = " \
    ${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}/*.cfg \
"

inherit update-rc.d

FILES_${PN}-run-domd += " \
    ${sysconfdir}/init.d/guest_domd \
"

FILES_${PN}-run-doma += " \
    ${sysconfdir}/init.d/guest_doma \
    ${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}/start_guest.sh \
"

FILES_${PN}-run-domf += " \
    ${sysconfdir}/init.d/guest_domf \
"

PACKAGES += " \
    ${PN}-run-domd \
    ${PN}-run-doma \
    ${PN}-run-domf \
"

# configure init.d scripts
INITSCRIPT_PACKAGES = "${PN}-run-domd ${PN}-run-doma ${PN}-run-domf"

INITSCRIPT_NAME_${PN}-run-domd = "guest_domd"
INITSCRIPT_PARAMS_${PN}-run-domd = "defaults 85"
INITSCRIPT_NAME_${PN}-run-domf = "guest_domf"
INITSCRIPT_PARAMS_${PN}-run-domf = "defaults 86"
INITSCRIPT_NAME_${PN}-run-doma = "guest_doma"
INITSCRIPT_PARAMS_${PN}-run-doma = "defaults 87"

do_install() {
    install -d ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}
    install -m 0744 ${WORKDIR}/${DOMD_CONFIG} ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}/domd.cfg
    install -m 0744 ${WORKDIR}/${DOMA_CONFIG} ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}/doma.cfg
    install -m 0744 ${WORKDIR}/domf.cfg ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}/domf.cfg

    install -d ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}
    install -d ${D}${sysconfdir}/init.d
    install -m 0744 ${WORKDIR}/guest_domd ${D}${sysconfdir}/init.d/
    install -m 0744 ${WORKDIR}/guest_doma ${D}${sysconfdir}/init.d/
    install -m 0744 ${WORKDIR}/start_guest.sh ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_SCRIPTS}/
    install -m 0744 ${WORKDIR}/guest_domf ${D}${sysconfdir}/init.d/
}
