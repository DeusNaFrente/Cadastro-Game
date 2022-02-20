 function handleLoginRequest(xhr, status, args, dlg, tb) {
                        if (args.validationFailed) {
                            PF(dlg).jq.effect("shake", {time: 5}, 100);
                        } else {
                            PF(dlg).hide();
                            PF(tb).clearFilters();
                            
                        }
                    }