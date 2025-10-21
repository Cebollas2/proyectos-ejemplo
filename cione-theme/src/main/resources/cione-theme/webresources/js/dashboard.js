document.addEventListener("DOMContentLoaded", function () {
	//$('.navbar').css('display','none');
    // Función para calcular el ancho de la caja de la leyenda
    // function getBoxWidth(labelOpts, fontSize) {
    //     return labelOpts.usePointStyle ? fontSize * Math.SQRT2 : labelOpts.boxWidth;
    // }

    // Extendemos la leyenda de los gráficos para modificar el espacio que hay entre el gráfico y la leyenda
    Chart.NewLegend = Chart.Legend.extend({
        afterFit: function () {
            this.height = this.height + 16; // Pones el padding que quieras
        },
    });

    // Creamos una nueva leyenda personalizada
    function createNewLegendAndAttach(chartInstance, legendOpts) {
        var legend = new Chart.NewLegend({
            ctx: chartInstance.chart.ctx,
            options: legendOpts,
            chart: chartInstance
        });

        if (chartInstance.legend) {
            Chart.layoutService.removeBox(chartInstance, chartInstance.legend);
            delete chartInstance.newLegend;
        }

        chartInstance.newLegend = legend;
        Chart.layoutService.addBox(chartInstance, legend);
    }

    // Hacemos uso de un plugin para usar la nueva leyenda personalizada
    Chart.plugins.register({
        beforeInit: function (chartInstance) {
            var legendOpts = chartInstance.options.legend;

            if (legendOpts) {
                createNewLegendAndAttach(chartInstance, legendOpts);
            }
        },
        beforeUpdate: function (chartInstance) {
            var legendOpts = chartInstance.options.legend;

            if (legendOpts) {
                legendOpts = Chart.helpers.configMerge(Chart.defaults.global.legend, legendOpts);

                if (chartInstance.newLegend) {
                    chartInstance.newLegend.options = legendOpts;
                } else {
                    createNewLegendAndAttach(chartInstance, legendOpts);
                }
            } else {
                Chart.layoutService.removeBox(chartInstance, chartInstance.newLegend);
                delete chartInstance.newLegend;
            }
        },
        afterEvent: function (chartInstance, e) {
            var legend = chartInstance.newLegend;
            if (legend) {
                legend.handleEvent(e);
            }
        }
    });

    // Aquí iniciamos el gráfico de manera dinámica con los datos del JSON donde le pasamos el id del canvas, el tipo de gráfico, los datos y las opciones
    function initializeChart(chartId, chartType, chartData, chartOptions) {
        const ctx = document.getElementById(chartId).getContext("2d");
        return new Chart(ctx, {
            type: chartType,
            data: chartData,
            options: chartOptions
        });
    }

	//var url = "/magnoliaAuthor/.rest/private/dashboard/v1/data-dashboard";

    // Cargamos los datos desde un archivo JSON


            //START GRAPH Estado actual Rappel Producto Propio
            // Cargamos los datos que están en el JSON para los labels y para los datasets
            
            
            const chartConsumptionActualLevelOwnProductOptions = {
                responsive: true,
                maintainAspectRatio: false,
                rotation: -Math.PI, // Comienza a pintar desde la parte superior
                circumference: Math.PI, // Mostramos solo 180 grados, ya que es un semicírculo
                legend: {
                    display: false
                },
                tooltips: {
			        callbacks: {
						title: function (tooltipItems, data) {
							// Pick first xLabel for now
							var title = '';
							if (tooltipItems.length > 0) {
								if (data.labels.length > 0 && tooltipItems[0].index < data.labels.length){
									title = data.labels[tooltipItems[0].index];
								}
							}
		
							return title;
						},
			            label: function (tooltipItem, data) {
			                const label = data.labels[tooltipItem.index];
			                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			                const formattedValue = value.toLocaleString('es-ES', {
			                    minimumFractionDigits: 2,
			                    maximumFractionDigits: 2
			                });
			                return formattedValue + " €"; // salto de línea + símbolo €
			            }
			        }, 
			        bodyFontSize: 10,           // Tamaño del texto
				    titleFontSize: 9,          // Tamaño del título
				    xPadding: 15,               // Espaciado horizontal interno
				    yPadding: 10,               // Espaciado vertical interno
				    cornerRadius: 6,            // Bordes más redondeados
				    caretSize: 6,               // Tamaño del triangulito inferior
				    backgroundColor: 'rgba(0, 0, 0, 0.8)', // Color de fondo
				    opacity: 1,
			    }
            };
            // Inicializamos el gráfico
            initializeChart("consumptionActualLevelOwnProduct", "doughnut", chartConsumptionActualLevelOwnProduct, chartConsumptionActualLevelOwnProductOptions);
            
            //END GRAPH Estado actual Rappel Producto Propio


            //START GRAPH Estado actual Rappel Directa
            
            const chartConsumptionActualLevelSupplierOptions = {
                responsive: true,
                maintainAspectRatio: false,
                rotation: -Math.PI,
                circumference: Math.PI,
                legend: {
                    display: false
                },
                tooltips: {
			        callbacks: {
						title: function (tooltipItems, data) {
							// Pick first xLabel for now
							var title = '';
							if (tooltipItems.length > 0) {
								if (data.labels.length > 0 && tooltipItems[0].index < data.labels.length){
									title = data.labels[tooltipItems[0].index];
								}
							}
		
							return title;
						},
			            label: function (tooltipItem, data) {
			                const label = data.labels[tooltipItem.index];
			                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			                const formattedValue = value.toLocaleString('es-ES', {
			                    minimumFractionDigits: 2,
			                    maximumFractionDigits: 2
			                });
			                return formattedValue + " €"; // salto de línea + símbolo €
			            }
			        }, 
			        bodyFontSize: 10,           // Tamaño del texto
				    titleFontSize: 9,          // Tamaño del título
				    xPadding: 10,               // Espaciado horizontal interno
				    yPadding: 10,               // Espaciado vertical interno
				    cornerRadius: 6,            // Bordes más redondeados
				    caretSize: 6,               // Tamaño del triangulito inferior
				    backgroundColor: 'rgba(0, 0, 0, 0.8)', // Color de fondo
				    opacity: 1,
			    }
            };
            initializeChart("consumptionActualLevelSupplier", "doughnut", chartConsumptionActualLevelSupplier, chartConsumptionActualLevelSupplierOptions);
            
            
            //END GRAPH Estado actual Rappel Directa


            //START GRAPH Siguiente estado Producto Propio
            
            const chartconsumptionNextLevelOwnProductOptions = {
                responsive: true,
                maintainAspectRatio: false,
                rotation: -Math.PI,
                circumference: Math.PI,
                legend: {
                    display: false
                },
                tooltips: {
			        callbacks: {
						title: function (tooltipItems, data) {
							// Pick first xLabel for now
							var title = '';
							if (tooltipItems.length > 0) {
								if (data.labels.length > 0 && tooltipItems[0].index < data.labels.length){
									title = data.labels[tooltipItems[0].index];
								}
							}
		
							return title;
						},
			            label: function (tooltipItem, data) {
			                const label = data.labels[tooltipItem.index];
			                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			                const formattedValue = value.toLocaleString('es-ES', {
			                    minimumFractionDigits: 2,
			                    maximumFractionDigits: 2
			                });
			                return formattedValue + " €"; // salto de línea + símbolo €
			            }
			        }, 
			        bodyFontSize: 10,           // Tamaño del texto
				    titleFontSize: 9,          // Tamaño del título
				    xPadding: 15,               // Espaciado horizontal interno
				    yPadding: 10,               // Espaciado vertical interno
				    cornerRadius: 6,            // Bordes más redondeados
				    caretSize: 6,               // Tamaño del triangulito inferior
				    backgroundColor: 'rgba(0, 0, 0, 0.8)', // Color de fondo
				    opacity: 1,
			    }
            };
            initializeChart("consumptionNextLevelOwnProduct", "doughnut", chartConsumptionNextLevelOwnProduct, chartconsumptionNextLevelOwnProductOptions);
            //END GRAPH Siguiente estado Producto Propio


            //START GRAPH Siguiente estado Rappel Directa
            const chartConsumptionNextLevelSupplierOptions = {
                responsive: true,
                maintainAspectRatio: false,
                rotation: -Math.PI,
                circumference: Math.PI,
                legend: {
                    display: false
                },
                tooltips: {
			        callbacks: {
						title: function (tooltipItems, data) {
							// Pick first xLabel for now
							var title = '';
							if (tooltipItems.length > 0) {
								if (data.labels.length > 0 && tooltipItems[0].index < data.labels.length){
									title = data.labels[tooltipItems[0].index];
								}
							}
		
							return title;
						},
			            label: function (tooltipItem, data) {
			                const label = data.labels[tooltipItem.index];
			                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			                const formattedValue = value.toLocaleString('es-ES', {
			                    minimumFractionDigits: 2,
			                    maximumFractionDigits: 2
			                });
			                return formattedValue + " €"; // salto de línea + símbolo €
			            }
			        }, 
			        bodyFontSize: 10,           // Tamaño del texto
				    titleFontSize: 9,          // Tamaño del título
				    xPadding: 15,               // Espaciado horizontal interno
				    yPadding: 10,               // Espaciado vertical interno
				    cornerRadius: 6,            // Bordes más redondeados
				    caretSize: 6,               // Tamaño del triangulito inferior
				    backgroundColor: 'rgba(0, 0, 0, 0.8)', // Color de fondo
				    opacity: 1,
			    }
            };
            initializeChart("consumptionNextLevelSupplier", "doughnut", chartConsumptionNextLevelSupplier, chartConsumptionNextLevelSupplierOptions);
            //END GRAPH Siguiente estado Rappel Directa


            //START GRAPH Mi consumo mensual
            
            const chartMonthlyOptions = {
                responsive: true,
                maintainAspectRatio: false,
                legend: {
                    display: true,
                    labels: {
                        padding: 20
                    },
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        min: 0,
                        max: 140000,
                        ticks: {
                            stepSize: 20000
                        }
                    }
                },
                tooltips: {
			        callbacks: {
						title: function (tooltipItems, data) {
							// Pick first xLabel for now
							var title = '';
							if (tooltipItems.length > 0) {
								console.log(tooltipItems[0].index);
								//if (data.labels.length > 0 && tooltipItems[0].index < data.labels.length){
									//title = data.datasets[tooltipItems[0].index].label;
								//}
							}
		
							return title;
						},
			            label: function (tooltipItem, data) {
							const a = data.datasets[tooltipItem.datasetIndex].label;
			                const label = data.labels[tooltipItem.index];
			                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			                const formattedValue = value.toLocaleString('es-ES', {
			                    minimumFractionDigits: 2,
			                    maximumFractionDigits: 2
			                });
			                return label +" (" + a + ")" + ": " + formattedValue + " €"; 
			            }
			        }, 
			        bodyFontSize: 10,           // Tamaño del texto
				    titleFontSize: 9,          // Tamaño del título
				    xPadding: 15,               // Espaciado horizontal interno
				    yPadding: 10,               // Espaciado vertical interno
				    cornerRadius: 6,            // Bordes más redondeados
				    caretSize: 6,               // Tamaño del triangulito inferior
				    backgroundColor: 'rgba(0, 0, 0, 0.8)', // Color de fondo
				    opacity: 1,
			    }
            };
            initializeChart("monthly", "bar", chartMonthly, chartMonthlyOptions);
            //END GRAPH Mi consumo mensual


            //START GRAPH Consumos acumulado anual
            const chartAnnualConsumptionOptions = {
                responsive: true,
                maintainAspectRatio: false,
                legend: {
                    display: true,
                    labels: {
                        padding: 20
                    },
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        min: 0,
                        max: 140000,
                        ticks: {
                            stepSize: 20000
                        }
                    }
                },
                tooltips: {
			        callbacks: {
						title: function (tooltipItems, data) {
							// Pick first xLabel for now
							var title = '';
							if (tooltipItems.length > 0) {
								console.log(tooltipItems[0].index);
								//if (data.labels.length > 0 && tooltipItems[0].index < data.labels.length){
									//title = data.datasets[tooltipItems[0].index].label;
								//}
							}
		
							return title;
						},
			            label: function (tooltipItem, data) {
							const a = data.datasets[tooltipItem.datasetIndex].label;
			                const label = data.labels[tooltipItem.index];
			                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			                const formattedValue = value.toLocaleString('es-ES', {
			                    minimumFractionDigits: 2,
			                    maximumFractionDigits: 2
			                });
			                return label +" (" + a + ")" + ": " + formattedValue + " €"; // salto de línea + símbolo €
			            }
			        }, 
			        bodyFontSize: 10,           // Tamaño del texto
				    titleFontSize: 9,          // Tamaño del título
				    xPadding: 15,               // Espaciado horizontal interno
				    yPadding: 10,               // Espaciado vertical interno
				    cornerRadius: 6,            // Bordes más redondeados
				    caretSize: 6,               // Tamaño del triangulito inferior
				    backgroundColor: 'rgba(0, 0, 0, 0.8)', // Color de fondo
				    opacity: 1,
			    }
            };
            initializeChart("annualConsumption", "bar", chartAnnualConsumption, chartAnnualConsumptionOptions);
            //END GRAPH Consumos acumulado anual


            //START GRAPH Consumos de los ultimos 12 meses
           
            const chartlastTwelveMonthsConsumptionOptions = {
                responsive: true,
                maintainAspectRatio: false,
                legend: {
                    display: true,
                    labels: {
                        padding: 20
                    },
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        min: 0,
                        max: 140000,
                        ticks: {
                            stepSize: 20000
                        }
                    }
                },
                tooltips: {
			        callbacks: {
						title: function (tooltipItems, data) {
							// Pick first xLabel for now
							var title = '';
							if (tooltipItems.length > 0) {
								console.log(tooltipItems[0].index);
								//if (data.labels.length > 0 && tooltipItems[0].index < data.labels.length){
									//title = data.datasets[tooltipItems[0].index].label;
								//}
							}
		
							return title;
						},
			            label: function (tooltipItem, data) {
							const a = data.datasets[tooltipItem.datasetIndex].label;
			                const label = data.labels[tooltipItem.index];
			                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			                const formattedValue = value.toLocaleString('es-ES', {
			                    minimumFractionDigits: 2,
			                    maximumFractionDigits: 2
			                });
			                return label +" (" + a + ")" + ": " + formattedValue + " €"; // salto de línea + símbolo €
			            }
			        }, 
			        bodyFontSize: 10,           // Tamaño del texto
				    titleFontSize: 9,          // Tamaño del título
				    xPadding: 15,               // Espaciado horizontal interno
				    yPadding: 10,               // Espaciado vertical interno
				    cornerRadius: 6,            // Bordes más redondeados
				    caretSize: 6,               // Tamaño del triangulito inferior
				    backgroundColor: 'rgba(0, 0, 0, 0.8)', // Color de fondo
				    opacity: 1,
			    }
            };
            initializeChart("lastTwelveMonthsConsumption", "bar", chartLastTwelveMonthsConsumption, chartlastTwelveMonthsConsumptionOptions);
            //END GRAPH Consumos de los ultimos 12 meses


            //START GRAPH Consumos por tipo del año actual
            
            const chartConsumptionByTypeOptions = {
                responsive: true,
                maintainAspectRatio: false,
                legend: {
                    display: true,
                    labels: {
                        padding: 20
                    },
                },
                scales: {
                    yAxes: [{ stacked: true }],
                    xAxes: [
                        {
                            stacked: true,
                        }
                    ],
                    x: {
                        stacked: true
                    },
                    y: {
                        beginAtZero: true,
                        min: 0,
                        max: 140000,
                        ticks: {
                            stepSize: 20000
                        },
                        stacked: true
                    }
                },
                tooltips: {
			        callbacks: {
						title: function (tooltipItems, data) {
							// Pick first xLabel for now
							var title = '';
							if (tooltipItems.length > 0) {
								console.log(tooltipItems[0].index);
								//if (data.labels.length > 0 && tooltipItems[0].index < data.labels.length){
									//title = data.datasets[tooltipItems[0].index].label;
								//}
							}
		
							return title;
						},
			            label: function (tooltipItem, data) {
							const a = data.datasets[tooltipItem.datasetIndex].label;
			                const label = data.labels[tooltipItem.index];
			                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			                const formattedValue = value.toLocaleString('es-ES', {
			                    minimumFractionDigits: 2,
			                    maximumFractionDigits: 2
			                });
			                return label +" (" + a + ")" + ": " + formattedValue + " €"; // salto de línea + símbolo €
			            }
			        }, 
			        bodyFontSize: 10,           // Tamaño del texto
				    titleFontSize: 9,          // Tamaño del título
				    xPadding: 15,               // Espaciado horizontal interno
				    yPadding: 10,               // Espaciado vertical interno
				    cornerRadius: 6,            // Bordes más redondeados
				    caretSize: 6,               // Tamaño del triangulito inferior
				    backgroundColor: 'rgba(0, 0, 0, 0.8)', // Color de fondo
				    opacity: 1,
			    }
            };
            initializeChart("consumptionByType", "bar", chartConsumptionByType, chartConsumptionByTypeOptions);
            //END GRAPH Consumos por tipo del año actual


            //START GRAPH Mis ahorros
            
            const chartSavingsOptions = {
                responsive: true,
                maintainAspectRatio: false,
                legend: {
                    display: true,
                    position: 'right',
                    labels: {
                        padding: 40
                    },
                },
                indexAxis: 'y',
                elements: {
                    bar: {
                        borderWidth: 2,
                    }
                },
				tooltips: {
					callbacks: {
						title: function(tooltipItems, data) {
							// Pick first xLabel for now
							var title = '';
		
							if (tooltipItems.length > 0) {
								if (tooltipItems[0].yLabel) {
									title = tooltipItems[0].yLabel;
								} else if (data.labels.length > 0 && tooltipItems[0].index < data.labels.length) {
									title = data.labels[tooltipItems[0].index];
								}
							}
		
							return title;
						},
						label: function(tooltipItem, data) {
							var datasetLabel = data.datasets[tooltipItem.datasetIndex].label || '';
							const value = tooltipItem.xLabel;
							const formattedValue = value.toLocaleString('es-ES', {
				                    minimumFractionDigits: 2,
				                    maximumFractionDigits: 2
				                });
							return datasetLabel + ': ' + formattedValue + " €";
						}
						
						
					}
				}               
            };
            initializeChart("mySavings", "horizontalBar", chartMySavings, chartSavingsOptions);
            //END GRAPH Mis ahorros


			const chartOptions = {
                responsive: true,
                maintainAspectRatio: false,
                legend: {
                    display: true,
                    labels: {
                        padding: 10
                    },
                },
			    tooltips: {
			        callbacks: {
			            label: function(tooltipItem, data) {
			                // Obtiene el valor
			                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			                
			                // Aplica formato
			                const formattedValue = value.toLocaleString('es-ES', {
			                    minimumFractionDigits: 2,
			                    maximumFractionDigits: 2
			                });
			
			                // Muestra etiqueta + valor
			                const label = data.labels[tooltipItem.index] || '';
			                return `${label}: ${formattedValue}`;
			            }
			        }
			    }
            }; 

			if (typeof consumptionMap !== 'undefined') {
				consumptionMap.forEach(function (chartId) {
		            const chartData = window["chart" + chartId];
		            if (chartData) {
		            	initializeChart(chartId, "doughnut", chartData, chartOptions);
		            }
		        });
			}

        
});